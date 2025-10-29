package cat.iundarigun.boaleitura.integration.book

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ReadingEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class BookGetEndpointTest(
    private val bookEntityFactory: BookEntityFactory,
    private val readingRepository: ReadingRepository
) : TestContainerBaseConfiguration() {

    @Test
    fun `get complete book by id successfully`() {
        val book = bookEntityFactory.buildAllAndSave()
        readingRepository.save(ReadingEntityFactory.build(book))

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .given()
            .pathParam("id", book.id)
            .`when`()
            .get("/books/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(BookResponse::class.java)

        Assertions.assertEquals(book.id, response.id)
        Assertions.assertEquals(book.title, response.title)
        Assertions.assertEquals(book.author.id, response.author.id)
        Assertions.assertEquals(book.author.name, response.author.name)
        Assertions.assertNotNull(response.genre)
        Assertions.assertEquals(book.genre?.id, response.genre?.id)
        Assertions.assertEquals(book.genre?.name, response.genre?.name)
        Assertions.assertEquals(book.numberOfPages, response.numberOfPages)
        Assertions.assertEquals(book.publisherYear, response.publisherYear)
        Assertions.assertNotNull(response.originalEdition)
        Assertions.assertEquals(book.originalTitle, response.originalEdition?.title)
        Assertions.assertEquals(book.originalLanguage, response.originalEdition?.language)
        Assertions.assertNotNull(response.saga)
        Assertions.assertEquals(book.saga?.id, response.saga?.saga?.id)
        Assertions.assertEquals(book.saga?.name, response.saga?.saga?.name)
        Assertions.assertEquals(book.sagaOrder, response.saga?.order)
        Assertions.assertEquals(book.sagaMainTitle, response.saga?.mainTitle)
        Assertions.assertEquals(book.isbn, response.isbn)
        Assertions.assertEquals(book.urlImageSmall, response.urlImageSmall)
        Assertions.assertEquals(book.urlImage, response.urlImage)
        Assertions.assertTrue(response.read)
    }

    @Test
    fun `get book by id without anything but author successfully`() {
        val book = bookEntityFactory.buildAndSave()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .given()
            .pathParam("id", book.id)
            .`when`()
            .get("/books/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(BookResponse::class.java)

        Assertions.assertEquals(book.id, response.id)
        Assertions.assertEquals(book.title, response.title)
        Assertions.assertEquals(book.author.id, response.author.id)
        Assertions.assertEquals(book.author.name, response.author.name)
        Assertions.assertNull(response.genre)
        Assertions.assertEquals(book.publisherYear, response.publisherYear)
        Assertions.assertNotNull(response.originalEdition)
        Assertions.assertEquals(book.originalTitle, response.originalEdition?.title)
        Assertions.assertEquals(book.originalLanguage, response.originalEdition?.language)
        Assertions.assertNull(response.saga)
        Assertions.assertEquals(book.isbn, response.isbn)
        Assertions.assertEquals(book.urlImageSmall, response.urlImageSmall)
        Assertions.assertEquals(book.urlImage, response.urlImage)
        Assertions.assertFalse(response.read)
    }

    @Test
    fun `get book by id with id does not exist`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .get("/books/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `get book with id as string`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .get("/books/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
