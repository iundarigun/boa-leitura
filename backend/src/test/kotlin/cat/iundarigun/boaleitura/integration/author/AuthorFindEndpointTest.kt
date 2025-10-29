package cat.iundarigun.boaleitura.integration.author

import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.factory.AuthorEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.HttpStatus

class AuthorFindEndpointTest(private val authorRepository: AuthorRepository) : TestContainerBaseConfiguration() {

    @BeforeEach
    fun cleanUp() {
        clearData()
        names().forEach { authorRepository.save(AuthorEntityFactory.build(it)) }
    }

    @Test
    fun `get all authors default successfully`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<AuthorResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(10, response.content.size)
        names().sorted().forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].name)
        }
    }

    @Test
    fun `get all authors descendent successfully`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("directionAsc", false)
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<AuthorResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(10, response.content.size)
        names().sorted().reversed().forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].name)
        }
    }

    @Test
    fun `get authors filtering by name successfully`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("name", "xpto")
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<AuthorResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(3, response.content.size)
        names().filter { it.contains("xpto", true) }
            .sorted()
            .forEachIndexed { index, name ->
                Assertions.assertEquals(name, response.content[index].name)
            }
    }

    @Test
    fun `get second page authors size 4 order by name successfully`() {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("page", "2")
            .queryParam("size", "4")
            .queryParam("order", "ID")
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<AuthorResponse>>() {})

        Assertions.assertEquals(2, response.page)
        Assertions.assertEquals(3, response.totalPages)
        Assertions.assertEquals(4, response.content.size)
        names().subList(4, 7).forEachIndexed { index, name ->
            Assertions.assertEquals(name, response.content[index].name)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1"])
    fun `get authors negative or zero page`(page: String) {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("page", page)
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1", "300", "1000"])
    fun `get authors wrong page size`(pageSize: String) {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("size", pageSize)
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @ParameterizedTest
    @ValueSource(strings = ["date", "authorName", "updateAt"])
    fun `get authors wrong order value`(orderValue: String) {
        val response = RestAssured.given()
            .header("X-User-Id", "1")
            .queryParam("order", orderValue)
            .`when`()
            .get("/authors")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    companion object {
        private fun names(): List<String> =
            listOf(
                "02 Name Two",
                "03 Name Three",
                "01 Name One",
                "05 Name Five",
                "06 Name Six XPTO more",
                "09 Name Nine",
                "04 Name Four Xpto",
                "10 Name Ten",
                "08 Name Eight xpTO ",
                "07 Name Seven"
            )
    }
}
