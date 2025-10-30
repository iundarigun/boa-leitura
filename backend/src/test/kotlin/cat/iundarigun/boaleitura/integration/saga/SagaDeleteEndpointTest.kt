package cat.iundarigun.boaleitura.integration.saga

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.factory.SagaEntityFactory
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class SagaDeleteEndpointTest(
    private val sagaRepository: SagaRepository,
    private val bookEntityFactory: BookEntityFactory,
) : TestContainerBaseConfiguration() {

    @Test
    fun `delete saga by id successfully`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        val count = sagaRepository.count()

        RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .delete("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        Assertions.assertEquals(count - 1, sagaRepository.count())
    }

    @Test
    fun `delete saga by id with books`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        bookEntityFactory.buildAndSave(saga = saga)

        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .delete("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
        Assertions.assertEquals(count, sagaRepository.count())
    }

    @Test
    fun `delete saga by id with id does not exist`() {
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .delete("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
        Assertions.assertEquals(count, sagaRepository.count())
    }

    @Test
    fun `delete saga with id as string`() {
        val count = sagaRepository.count()

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .delete("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
        Assertions.assertEquals(count, sagaRepository.count())
    }
}
