package cat.iundarigun.boaleitura.integration.tbr

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.configuration.FakerConfiguration.randomId
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ToBeReadEntityFactory
import cat.iundarigun.boaleitura.factory.ToBeReadRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.ToBeReadRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class ToBeReadPostEndpointTest(
    private val toBeReadRepository: ToBeReadRepository,
    private val bookEntityFactory: BookEntityFactory
) : TestContainerBaseConfiguration() {

    @Test
    fun `create toBeRead successfully`() {
        val book = bookEntityFactory.buildAllAndSave()
        val request = ToBeReadRequestFactory.build(book.id)
        val count = executeInContext { toBeReadRepository.count() }
        val position = executeInContext { (toBeReadRepository.findMaxPosition() ?: 0) + 1 }

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/tbr")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(ToBeReadResponse::class.java)

        Assertions.assertEquals(count + 1, executeInContext { toBeReadRepository.count() })
        Assertions.assertEquals(request.bookId, response.book.id)
        Assertions.assertEquals(request.bought, response.bought)
        Assertions.assertEquals(request.platforms, response.platforms)
        Assertions.assertEquals(request.tags, response.tags)
        Assertions.assertEquals(request.notes, response.notes)
        Assertions.assertFalse(response.done)
        Assertions.assertEquals(position, response.position)
    }

    @Test
    fun `create toBeRead with non-existing book`() {
        val request = ToBeReadRequestFactory.build(bookId = FAKER.randomId())
        val count = executeInContext { toBeReadRepository.count() }

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/tbr")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, executeInContext { toBeReadRepository.count() })
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `create toBeRead already exists`() {
        val book = bookEntityFactory.buildAllAndSave()
        executeInContext { toBeReadRepository.save(ToBeReadEntityFactory.build(book)) }
        val request = ToBeReadRequestFactory.build(book.id)
        val count = executeInContext { toBeReadRepository.count() }

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/tbr")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, executeInContext { toBeReadRepository.count() })
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }
}
