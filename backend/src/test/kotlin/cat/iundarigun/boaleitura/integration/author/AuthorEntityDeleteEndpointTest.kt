package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.AuthorEntityFactory
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class AuthorEntityDeleteEndpointTest(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) : TestContainerBaseConfiguration() {

    @Test
    fun `delete author by id successfully`() {
        val author = authorRepository.save(AuthorEntityFactory.build())
        val count = authorRepository.count()

        RestAssured.given()
            .given()
            .pathParam("id", author.id)
            .`when`()
            .delete("/authors/{id}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        Assertions.assertEquals(count - 1, authorRepository.count())
    }

    @Test
    fun `delete author by id with books`() {
        val author = authorRepository.save(AuthorEntityFactory.build())
        bookRepository.save(BookEntityFactory.build(author = author))

        val count = authorRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", author.id)
            .`when`()
            .delete("/authors/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals(count, authorRepository.count())
    }

    @Test
    fun `delete author by id with id does not exist`() {
        val count = authorRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .delete("/authors/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
        Assertions.assertEquals(count, authorRepository.count())
    }

    @Test
    fun `delete author with id as string`() {
        val count = authorRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .delete("/authors/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals(count, authorRepository.count())
    }
}
