package cat.iundarigun.boaleitura.integration.book

import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ReadingEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.HttpStatus
import java.text.Collator
import java.util.*

class BookFindEndpointTest(
    private val bookEntityFactory: BookEntityFactory,
    private val bookRepository: BookRepository,
    private val readingRepository: ReadingRepository
) : TestContainerBaseConfiguration() {

    @BeforeEach
    fun cleanUp() {
        clearData()
    }

    private fun createRandomBooks(quantity: Int = 10, delayMs: Long = 0): List<BookEntity> {
        return (1..quantity).map {
            if (delayMs > 0) {
                Thread.sleep(delayMs)
            }
            bookEntityFactory.buildAllAndSave()
        }
    }

    @Test
    fun `get all books default successfully`() {
        val randomBooks = createRandomBooks(20)
            .map { it.title }

        val response = RestAssured.given()
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(randomBooks.size, response.content.size)
        randomBooks
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .forEachIndexed { index, name ->
                Assertions.assertEquals(name, response.content[index].title)
            }
    }

    @Test
    fun `get all books ordered by author successfully`() {
        val randomBooks = createRandomBooks()
            .map { it.author.name }

        val response = RestAssured.given()
            .queryParam("order", "AUTHOR")
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(randomBooks.size, response.content.size)
        randomBooks
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].author)
        }
    }

    @Test
    fun `get all Saga reverse ordered by author successfully`() {
        val randomBooks = createRandomBooks()
            .map { it.saga?.name ?: "" }

        val response = RestAssured.given()
            .queryParam("order", "SAGA")
            .queryParam("directionAsc", false)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(randomBooks.size, response.content.size)
        randomBooks
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .reversed().forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].saga)
        }
    }

    @Test
    fun `get all genre reverse ordered by author successfully`() {
        val randomBooks = createRandomBooks()
            .map { it.genre?.name ?: "" }

        val response = RestAssured.given()
            .queryParam("order", "GENRE")
            .queryParam("directionAsc", false)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(randomBooks.size, response.content.size)
        randomBooks
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .reversed().forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].genre)
        }
    }

    @Test
    fun `get all created_at reverse ordered by author successfully`() {
        val randomBooks = createRandomBooks(delayMs = 100)
            .map { it.createdAt }

        val response = RestAssured.given()
            .queryParam("order", "CREATED_AT")
            .queryParam("directionAsc", false)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(randomBooks.size, response.content.size)
        randomBooks.sorted().reversed().forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].createdAt)
        }
    }

    @Test
    fun `get books filtering by name successfully`() {
        val createRandomBooks = createRandomBooks(20)
        bookRepository.save(createRandomBooks[2].also {
            it.title = "02 Title two with XPTO and more"
        })
        bookRepository.save(createRandomBooks[4].also {
            it.title = "04 Title four with xpTO and more"
        })
        bookRepository.save(createRandomBooks[8].also {
            it.title = "08 ${it.title}"
            it.originalTitle = "08 Title four with XPto and more"
        })
        val titles = listOf(
            createRandomBooks[2].title,
            createRandomBooks[4].title,
            createRandomBooks[8].title
        )
        val response = RestAssured.given()
            .queryParam("title", "xpto")
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(3, response.content.size)
        titles
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .forEachIndexed { index, title ->
            Assertions.assertEquals(title, response.content[index].title)
        }
    }

    @Test
    fun `get books filtering by read successfully`() {
        val createRandomBooks = createRandomBooks(20)
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[2]))
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[4]))
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[8]))

        val titles = listOf(
            createRandomBooks[2].title,
            createRandomBooks[4].title,
            createRandomBooks[8].title
        )
        val response = RestAssured.given()
            .queryParam("read", true)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(3, response.content.size)
        titles
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .forEachIndexed { index, title ->
                Assertions.assertEquals(title, response.content[index].title)
            }
    }

    @Test
    fun `get books filtering by non-read successfully`() {
        val createRandomBooks = createRandomBooks(6)
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[0]))
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[1]))
        readingRepository.save(ReadingEntityFactory.build(createRandomBooks[2]))

        val titles = listOf(
            createRandomBooks[3].title,
            createRandomBooks[4].title,
            createRandomBooks[5].title
        )
        val response = RestAssured.given()
            .queryParam("read", false)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(3, response.content.size)
        titles
            // using sortedWith to specify locale
            .sortedWith { a, b -> Collator.getInstance(Locale.US).compare(a, b) }
            .forEachIndexed { index, title ->
                Assertions.assertEquals(title, response.content[index].title)
            }
    }

    @Test
    fun `get second page books size 4 order by name successfully`() {
        val randomBooks = createRandomBooks(10).map { it.id }
        val response = RestAssured.given()
            .queryParam("page", "2")
            .queryParam("size", "4")
            .queryParam("order", "ID")
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<BookSummaryResponse>>() {})

        Assertions.assertEquals(2, response.page)
        Assertions.assertEquals(3, response.totalPages)
        Assertions.assertEquals(4, response.content.size)
        randomBooks.sorted().subList(4, 7).forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].id)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1"])
    fun `get books negative or zero page`(page: String) {
        val response = RestAssured.given()
            .queryParam("page", page)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1", "300", "1000"])
    fun `get books wrong page size`(pageSize: String) {
        val response = RestAssured.given()
            .queryParam("size", pageSize)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @ParameterizedTest
    @ValueSource(strings = ["date", "bookName", "updateAt"])
    fun `get books wrong order value`(orderValue: String) {
        val response = RestAssured.given()
            .queryParam("order", orderValue)
            .`when`()
            .get("/books")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
