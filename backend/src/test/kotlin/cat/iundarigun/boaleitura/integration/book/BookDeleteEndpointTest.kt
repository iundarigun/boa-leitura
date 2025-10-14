package cat.iundarigun.boaleitura.integration.book

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.FakerConfiguration.randomId
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ReadingEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class BookDeleteEndpointTest(
    private val bookRepository: BookRepository,
    private val bookEntityFactory: BookEntityFactory,
    private val readingRepository: ReadingRepository
) : TestContainerBaseConfiguration() {

    @Test
    fun `delete book by id successfully`() {
        val book = bookEntityFactory.buildAllAndSave()
        val count = bookRepository.count()

        RestAssured.given()
            .given()
            .pathParam("id", book.id)
            .`when`()
            .delete("/books/{id}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        Assertions.assertEquals(count - 1, bookRepository.count())
    }

    @Test
    fun `delete book by id with reads`() {
        val book = bookEntityFactory.buildAllAndSave()
        readingRepository.save(ReadingEntityFactory.build(book))

        val count = bookRepository.count()

        val response = RestAssured.given()
            .given()
            .pathParam("id", book.id)
            .`when`()
            .delete("/books/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("The Book ${book.id} can not be deleted!", response.message)
        Assertions.assertEquals(count, bookRepository.count())
    }

    @Test
    fun `delete book by id with id does not exist`() {
        val count = bookRepository.count()
        val bookId = FakerConfiguration.FAKER.randomId()

        val response = RestAssured.given()
            .given()
            .pathParam("id", bookId)
            .`when`()
            .delete("/books/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
        Assertions.assertEquals("Book with id $bookId not found", response.message)
        Assertions.assertEquals(count, bookRepository.count())
    }
}
