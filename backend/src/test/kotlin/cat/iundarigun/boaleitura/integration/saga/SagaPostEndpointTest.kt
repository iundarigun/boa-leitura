package cat.iundarigun.boaleitura.integration.saga

import cat.iundarigun.boaleitura.factory.SagaRequestFactory
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class SagaPostEndpointTest(private val sagaRepository: SagaRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `create saga successfully`() {
        val request = SagaRequestFactory.build()
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/sagas")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .`as`(SagaResponse::class.java)

        Assertions.assertEquals(count + 1, sagaRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.totalMainTitles, response.totalMainTitles)
        Assertions.assertEquals(request.totalComplementaryTitles, response.totalComplementaryTitles)
        Assertions.assertEquals(request.concluded, response.concluded)
    }

    @Test
    fun `create saga without name`() {
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"totalMainTitles": 5, "concluded": "true"}""")
            .`when`()
            .post("/sagas")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, sagaRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create saga with invalid totalMainTitles`() {
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Saga name", "totalMainTitles": "FIVE"}""")
            .`when`()
            .post("/sagas")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, sagaRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create saga with small name`() {
        val request = SagaRequestFactory.build().copy(name = "SG")
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/sagas")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, sagaRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `create saga with negative totalMainTitles`() {
        val request = SagaRequestFactory.build().copy(totalMainTitles = -5)
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`()
            .post("/sagas")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(count, sagaRepository.count())
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
