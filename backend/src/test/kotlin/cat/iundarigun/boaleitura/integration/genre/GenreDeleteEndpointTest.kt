package cat.iundarigun.boaleitura.integration.genre

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.GenreEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GenreDeleteEndpointTest(
    private val genreRepository: GenreRepository,
    private val bookEntityFactory: BookEntityFactory
) : TestContainerBaseConfiguration() {

    @Test
    fun `delete genre by id successfully`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val count = genreRepository.count()

        RestAssured.given()
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .delete("/genres/{id}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        Assertions.assertEquals(count - 1, genreRepository.count())
    }

    @Test
    fun `delete genre by id with children`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        genreRepository.save(GenreEntityFactory.build(genre))

        val count = genreRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .delete("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals(count, genreRepository.count())
    }

    @Test
    fun `delete genre by id with books`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        bookEntityFactory.buildAndSave(genre = genre)

        val count = genreRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .delete("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals(count, genreRepository.count())
    }

    @Test
    fun `delete genre by id with id does not exist`() {
        val count = genreRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .delete("/genres/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
        Assertions.assertEquals(count, genreRepository.count())
    }

    @Test
    fun `delete genre with id as string`() {
        val count = genreRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .delete("/genres/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals(count, genreRepository.count())
    }
}
