package cat.iundarigun.boaleitura.integration.saga

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.factory.SagaEntityFactory
import cat.iundarigun.boaleitura.factory.SagaRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class SagaEntityPutEndpointTest(private val sagaRepository: SagaRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `update saga successfully`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        val request = SagaRequestFactory.build()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(SagaResponse::class.java)

        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.totalMainTitles, response.totalMainTitles)
        Assertions.assertEquals(request.totalComplementaryTitles, response.totalComplementaryTitles)
        Assertions.assertEquals(request.concluded, response.concluded)
        val updated = sagaRepository.findById(saga.id).orElseThrow()
        Assertions.assertEquals(request.name, updated.name)
        Assertions.assertEquals(request.totalMainTitles, updated.totalMainTitles)
        Assertions.assertEquals(request.totalComplementaryTitles, updated.totalComplementaryTitles)
        Assertions.assertEquals(request.concluded, updated.concluded)
        Assertions.assertEquals(saga.version + 1, updated.version)
    }

    @Test
    fun `update saga with name already exists`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        val other = sagaRepository.save(SagaEntityFactory.build())
        val request = SagaRequestFactory.build().copy(name = other.name)

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `update saga with id does not exist`() {
        val request = SagaRequestFactory.build()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `update saga with id as string`() {
        val request = SagaRequestFactory.build()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update saga without name`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"totalMainTitles": 5, "concluded": "true"}""")
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update saga with invalid totalMainTitles`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Saga name", "totalMainTitles": "FIVE"}""")
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update saga with small name`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        val request = SagaRequestFactory.build().copy(name = "SM")

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .put("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
