package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.factory.AuthorRequestFactory
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.repository.AuthorRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class AuthorPostEndpointTest(private val authorRepository: AuthorRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `create author successfully`() {
        val request = AuthorRequestFactory.build()
        val count = authorRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/authors")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(AuthorResponse::class.java)

        Assertions.assertEquals(count + 1, authorRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.gender, response.gender)
        Assertions.assertEquals(request.nationality, response.nationality)
    }

    @Test
    fun `create author without name`() {
        val count = authorRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"gender": "MALE", "nationality": "BRAZIL"}""")
            .`when`()
            .post("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, authorRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create author with non-existing gender`() {
        val count = authorRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Author name", "gender": "BAD GENDER"}""")
            .`when`()
            .post("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, authorRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create author with small name`() {
        val request = AuthorRequestFactory.build().copy(name = "SM")
        val count = authorRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, authorRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
