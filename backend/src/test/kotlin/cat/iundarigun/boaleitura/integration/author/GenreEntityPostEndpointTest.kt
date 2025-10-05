package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.factory.GenreEntityFactory
import cat.iundarigun.boaleitura.factory.GenreRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class GenreEntityPostEndpointTest(private val genreRepository: GenreRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `create genre successfully`() {
        val request = GenreRequestFactory.build()
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(count + 1, genreRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertNull(response.parent)
    }

    @Test
    fun `create genre with parentId successfully`() {
        val parent = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequestFactory.build(parent.id)
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(count + 1, genreRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertNotNull(response.parent)
        Assertions.assertEquals(request.parentGenreId, response.parent?.id)
        Assertions.assertEquals(parent.name, response.parent?.name)
        Assertions.assertNull(response.parent?.parent)
    }

    @Test
    fun `create genre with existing name`() {
        val existing = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequest(name = existing.name)
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `create genre with non-existing parent`() {
        val request = GenreRequestFactory.build(
            parentGenreId = FakerConfiguration.FAKER.number().numberBetween(10_000L, 99_999L)
        )
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `create genre without name`() {
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"parentGenreId": 10}""")
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create genre with non-number parentId`() {
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Genre name", "parentGenreId": "BAD GENDER"}""")
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create genre with small name`() {
        val request = GenreRequest(name = "SM")
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/genres")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
