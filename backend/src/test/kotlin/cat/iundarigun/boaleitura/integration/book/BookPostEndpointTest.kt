package cat.iundarigun.boaleitura.integration.book

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.configuration.FakerConfiguration.randomId
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
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

class BookPostEndpointTest(
    private val bookRepository: BookRepository,
    private val bookRequestFactory: BookRequestFactory,
    private val bookEntityFactory: BookEntityFactory
) : TestContainerBaseConfiguration() {

    @Test
    fun `create book successfully`() {
        val request = bookRequestFactory.build()
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(BookResponse::class.java)

        Assertions.assertEquals(count + 1, bookRepository.count())
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
    }

    @Test
    fun `create book without optionals successfully`() {
        val request = bookRequestFactory.build(
            sagaId = null,
            originalEdition = null,
            urlImage = null,
            urlImageSmall = null
        )
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(BookResponse::class.java)

        Assertions.assertEquals(count + 1, bookRepository.count())
        Assertions.assertEquals(request.title, response.title)
        Assertions.assertEquals(request.authorId, response.author.id)
        Assertions.assertEquals(request.genreId, response.genre?.id)
        Assertions.assertEquals(request.language, response.language)
        Assertions.assertEquals(request.numberOfPages, response.numberOfPages)
        Assertions.assertEquals(request.publisherYear, response.publisherYear)
        Assertions.assertNull(response.originalEdition)
        Assertions.assertNull(response.saga)
        Assertions.assertNull(response.urlImage)
        Assertions.assertNull(response.urlImageSmall)
    }

    @Test
    fun `create book isbn already exists`() {
        val book = bookEntityFactory.buildAllAndSave()
        val request = bookRequestFactory.build(isbn = book.isbn!!)
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("The Book ${request.isbn} already exists!", response.message)
    }

    @Test
    fun `create book with non-existing author`() {
        val request = bookRequestFactory.build(authorId = FAKER.randomId())
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("Author with id ${request.authorId} not found", response.message)
    }

    @Test
    fun `create book with non-existing genre`() {
        val request = bookRequestFactory.build(genreId = FAKER.randomId())
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("Genre with id ${request.genreId} not found", response.message)
    }

    @Test
    fun `create book with non-existing saga`() {
        val request = bookRequestFactory.build(sagaId = FAKER.randomId())
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals("Saga with id ${request.saga?.id} not found", response.message)
    }

    @Test
    fun `create book with blank title`() {
        val request = bookRequestFactory.build(title = "")
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals("title: size must be between 1 and 255", response.message)
    }

    @Test
    fun `create book with long title`() {
        val request = bookRequestFactory.build(title = FAKER.lorem().characters(300))
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals("title: size must be between 1 and 255", response.message)
    }

    @Test
    fun `create book with blank original title`() {
        val originalEdition = BookOriginalEditionModel(
            title = "",
            language = FAKER.languageCode().iso639()
        )
        val request = bookRequestFactory.build(originalEdition = originalEdition)
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals("originalEdition.title: size must be between 1 and 255", response.message)
    }

    @Test
    fun `create book with invalid image url`() {
        val request = bookRequestFactory.build(urlImage = FAKER.lorem().characters(100))
        val count = bookRepository.count()

        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, bookRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals("urlImage: must be a valid URL", response.message)
    }
}