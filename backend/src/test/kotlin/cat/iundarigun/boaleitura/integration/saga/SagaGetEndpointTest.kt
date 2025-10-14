package cat.iundarigun.boaleitura.integration.saga

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.SagaEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class SagaGetEndpointTest(
    private val sagaRepository: SagaRepository,
    private val bookEntityFactory: BookEntityFactory
) : TestContainerBaseConfiguration() {

    @Test
    fun `get saga by id successfully`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())

        val response = RestAssured.given()
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .get("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(SagaResponse::class.java)

        Assertions.assertEquals(saga.id, response.id)
        Assertions.assertEquals(saga.name, response.name)
        Assertions.assertEquals(saga.totalMainTitles, response.totalMainTitles)
        Assertions.assertEquals(saga.totalComplementaryTitles, response.totalComplementaryTitles)
        Assertions.assertEquals(saga.concluded, response.concluded)
        Assertions.assertNotNull(response.mainTitles)
        Assertions.assertTrue(response.mainTitles!!.isEmpty())
        Assertions.assertNotNull(response.complementaryTitles)
        Assertions.assertTrue(response.complementaryTitles!!.isEmpty())
    }

    @Test
    fun `get saga by id with books successfully`() {
        val saga = sagaRepository.save(SagaEntityFactory.build())
        val book4 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 4.0, sagaMainTitle = true)
        val book2 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 2.0, sagaMainTitle = true)
        val book1 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 1.0, sagaMainTitle = true)
        val book3 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 3.0, sagaMainTitle = true)
        val book3Point5 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 3.5, sagaMainTitle = false)
        val book1Point5 = bookEntityFactory.buildAndSave(saga = saga, sagaOrder = 1.5, sagaMainTitle = false)

        val response = RestAssured.given()
            .given()
            .pathParam("id", saga.id)
            .`when`()
            .get("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(SagaResponse::class.java)

        Assertions.assertEquals(saga.id, response.id)
        Assertions.assertEquals(saga.name, response.name)
        Assertions.assertEquals(saga.totalMainTitles, response.totalMainTitles)
        Assertions.assertEquals(saga.totalComplementaryTitles, response.totalComplementaryTitles)
        Assertions.assertEquals(saga.concluded, response.concluded)
        Assertions.assertNotNull(response.mainTitles)
        Assertions.assertTrue(response.mainTitles!!.isNotEmpty())
        Assertions.assertEquals(4, response.mainTitles!!.size)
        Assertions.assertEquals(book1.title, response.mainTitles!![0])
        Assertions.assertEquals(book2.title, response.mainTitles!![1])
        Assertions.assertEquals(book3.title, response.mainTitles!![2])
        Assertions.assertEquals(book4.title, response.mainTitles!![3])
        Assertions.assertNotNull(response.complementaryTitles)
        Assertions.assertTrue(response.complementaryTitles!!.isNotEmpty())
        Assertions.assertEquals(2, response.complementaryTitles!!.size)
        Assertions.assertEquals(book1Point5.title, response.complementaryTitles!![0])
        Assertions.assertEquals(book3Point5.title, response.complementaryTitles!![1])
    }

    @Test
    fun `get saga by id with id does not exist`() {
        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .get("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `get saga with id as string`() {
        val response = RestAssured.given()
            .given()
            .pathParam("id", FakerConfiguration.FAKER.name().firstName())
            .`when`()
            .get("/sagas/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
