package cat.iundarigun.boaleitura.integration.genre

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.factory.GenreEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GenreEntityGetEndpointTest(private val genreRepository: GenreRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `get genre by id successfully`() {
        val genre = genreRepository.save(GenreEntityFactory.build())

        val response = RestAssured.given()
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .get("/genres/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(genre.id, response.id)
        Assertions.assertEquals(genre.name, response.name)
        Assertions.assertNull(response.parent)
    }

    @Test
    fun `get genre by id with parent successfully`() {
        val parent = genreRepository.save(GenreEntityFactory.build())
        val genre = genreRepository.save(GenreEntityFactory.build(parent))

        val response = RestAssured.given()
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .get("/genres/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(genre.id, response.id)
        Assertions.assertEquals(genre.name, response.name)
        Assertions.assertNotNull(response.parent)
        Assertions.assertEquals(parent.id, response.parent?.id)
        Assertions.assertEquals(parent.name, response.parent?.name)
    }

    @Test
    fun `get genre by id with id does not exist`() {
        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .get("/genres/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `get genre with id as string`() {
        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .get("/genres/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
