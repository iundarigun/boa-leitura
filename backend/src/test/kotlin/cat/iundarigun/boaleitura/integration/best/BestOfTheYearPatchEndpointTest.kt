package cat.iundarigun.boaleitura.integration.best

import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.enums.BestOfTheYearFieldEnum
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearFieldRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ReadingEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.BestOfTheYearRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalDate
import java.time.Month

class BestOfTheYearPatchEndpointTest(
    private val readingRepository: ReadingRepository,
    private val bookEntityFactory: BookEntityFactory,
    private val bestOfTheYearRepository: BestOfTheYearRepository
) : TestContainerBaseConfiguration() {

    @Test
    fun `add best of the a month`() {
        dataFactory.clean()
        val currentYear = LocalDate.now().year
        val reading = executeInContext {
            val book = bookEntityFactory.buildAllAndSave()
            readingRepository.save(
                ReadingEntityFactory.build(
                    book,
                    LocalDate.of(currentYear, Month.JANUARY, 1)
                )
            )
        }
        val count = executeInContext { bestOfTheYearRepository.count() }

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(BestOfTheYearFieldRequest(BestOfTheYearFieldEnum.JANUARY, reading.id))
            .`when`()
            .pathParam("year", currentYear)
            .patch("/bests/{year}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(BestOfTheYearResponse::class.java)

        Assertions.assertEquals(count + 1, executeInContext { bestOfTheYearRepository.count() })
        Assertions.assertEquals(currentYear, response.year)
        Assertions.assertNotNull(response.january?.id)
        Assertions.assertEquals(reading.id, response.january?.id)
        Assertions.assertNull(response.february)
        Assertions.assertNull(response.march)
        Assertions.assertNull(response.april)
        Assertions.assertNull(response.may)
        Assertions.assertNull(response.june)
        Assertions.assertNull(response.july)
        Assertions.assertNull(response.august)
        Assertions.assertNull(response.september)
        Assertions.assertNull(response.october)
        Assertions.assertNull(response.november)
        Assertions.assertNull(response.december)
        Assertions.assertNull(response.quarterOne)
        Assertions.assertNull(response.quarterTwo)
        Assertions.assertNull(response.quarterThree)
        Assertions.assertNull(response.quarterFour)
        Assertions.assertNull(response.bestOfTheYear)
    }
}