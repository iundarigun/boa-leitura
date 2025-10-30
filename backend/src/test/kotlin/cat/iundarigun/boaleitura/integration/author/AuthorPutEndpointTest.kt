package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.AuthorEntityFactory
import cat.iundarigun.boaleitura.factory.AuthorRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class AuthorPutEndpointTest(private val authorRepository: AuthorRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `update author successfully`() {
        val author = authorRepository.save(AuthorEntityFactory.build())
        val request = AuthorRequestFactory.build()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", author.id)
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(AuthorResponse::class.java)

        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.gender, response.gender)
        Assertions.assertEquals(request.nationality, response.nationality)
        val updated = authorRepository.findById(author.id).orElseThrow()
        Assertions.assertEquals(request.name, updated.name)
        Assertions.assertEquals(request.gender, updated.gender)
        Assertions.assertEquals(request.nationality, updated.nationality)
        Assertions.assertEquals(author.version + 1, updated.version)
    }

    @Test
    fun `update author with id does not exist`() {
        val request = AuthorRequestFactory.build()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `update author with id as string`() {
        val request = AuthorRequestFactory.build()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update author without name`() {
        val author = authorRepository.save(AuthorEntityFactory.build())

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"gender": "MALE", "nationality": "BRAZIL"}""")
            .given()
            .pathParam("id", author.id)
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update author with non-existing gender`() {
        val author = authorRepository.save(AuthorEntityFactory.build())

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Author name", "gender": "BAD GENDER"}""")
            .given()
            .pathParam("id", author.id)
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update author with small name`() {
        val author = authorRepository.save(AuthorEntityFactory.build())
        val request = AuthorRequestFactory.build().copy(name = "SM")

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", author.id)
            .`when`()
            .put("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
