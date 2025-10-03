package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.AuthorFactory
import cat.iundarigun.boaleitura.factory.AuthorRequestFactory
import cat.iundarigun.boaleitura.repository.AuthorRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class AuthorGetEndpointTest(private val authorRepository: AuthorRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `get author by id successfully`() {
        val author = authorRepository.save(AuthorFactory.build())

        val response = RestAssured.given()
            .given()
            .pathParam("id", author.id)
            .`when`()
            .get("/authors/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(AuthorResponse::class.java)

        Assertions.assertEquals(author.id, response.id)
        Assertions.assertEquals(author.name, response.name)
        Assertions.assertEquals(author.gender, response.gender)
        Assertions.assertEquals(author.nationality, response.nationality)
    }

    @Test
    fun `get author by id with id does not exist`() {
        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .get("/authors/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `get author with id as string`() {
        val request = AuthorRequestFactory.build()

        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .get("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
