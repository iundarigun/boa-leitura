package cat.iundarigun.boaleitura.integration.book

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.FakerConfiguration.randomId
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.BookRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class BookPutEndpointTest(
    private val bookRepository: BookRepository,
    private val bookRequestFactory: BookRequestFactory,
    private val bookEntityFactory: BookEntityFactory
) : TestContainerBaseConfiguration() {

    @Test
    fun `update book successfully`() {
        val book = bookEntityFactory.buildAllAndSave()
        val request = bookRequestFactory.build()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", book.id)
            .`when`()
            .put("/books/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(BookResponse::class.java)

        Assertions.assertEquals(request.title, response.title)
        Assertions.assertEquals(request.authorId, response.author.id)
        Assertions.assertEquals(request.genreId, response.genre?.id)
        Assertions.assertEquals(request.language, response.language)
        Assertions.assertEquals(request.numberOfPages, response.numberOfPages)
        Assertions.assertEquals(request.publisherYear, response.publisherYear)
        Assertions.assertNotNull(response.originalEdition)
        Assertions.assertEquals(request.originalEdition?.title, response.originalEdition?.title)
        Assertions.assertEquals(request.originalEdition?.language, response.originalEdition?.language)
        Assertions.assertNotNull(response.saga)
        Assertions.assertEquals(request.saga?.id, response.saga?.saga?.id)
        Assertions.assertEquals(request.saga?.order, response.saga?.order)
        Assertions.assertEquals(request.saga?.mainTitle, response.saga?.mainTitle)
        Assertions.assertEquals(request.urlImage, response.urlImage)
        Assertions.assertEquals(request.urlImageSmall, response.urlImageSmall)
        val updated = bookRepository.findById(book.id).orElseThrow()
        Assertions.assertEquals(request.title, updated.title)
        Assertions.assertEquals(request.authorId, updated.author.id)
        Assertions.assertEquals(request.genreId, updated.genre?.id)
        Assertions.assertEquals(request.language, updated.language)
        Assertions.assertEquals(request.numberOfPages, updated.numberOfPages)
        Assertions.assertEquals(request.publisherYear, updated.publisherYear)
        Assertions.assertEquals(request.originalEdition?.title, updated.originalTitle)
        Assertions.assertEquals(request.originalEdition?.language, updated.originalLanguage)
        Assertions.assertEquals(request.saga?.id, updated.saga?.id)
        Assertions.assertEquals(request.saga?.order, updated.sagaOrder)
        Assertions.assertEquals(request.saga?.mainTitle, updated.sagaMainTitle)
        Assertions.assertEquals(request.urlImage, updated.urlImage)
        Assertions.assertEquals(request.urlImageSmall, updated.urlImageSmall)
        Assertions.assertEquals(book.version + 1, updated.version)
    }

    @Test
    fun `update book with id does not exist`() {
        val request = bookRequestFactory.build()
        val bookId = FakerConfiguration.FAKER.randomId()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", bookId)
            .`when`()
            .put("/books/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
        Assertions.assertEquals("Book with id $bookId not found", response.message)
    }

    @Test
    fun `update book with existing isbn`() {
        val book = bookEntityFactory.buildAllAndSave()
        val book2 = bookEntityFactory.buildAllAndSave()
        val request = bookRequestFactory.build(isbn = book2.isbn!!)

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", book.id)
            .`when`()
            .put("/books/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("The Book ${request.isbn} already exists!", response.message)
    }
}
