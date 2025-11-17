package cat.iundarigun.boaleitura.integration.best

import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import cat.iundarigun.boaleitura.factory.BookEntityFactory
import cat.iundarigun.boaleitura.factory.ReadingEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.entity.BestOfTheYearEntity
import cat.iundarigun.boaleitura.infrastructure.database.repository.BestOfTheYearRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalDate

class BestOfTheYearGetEndpointTest(
    private val readingRepository: ReadingRepository,
    private val bookEntityFactory: BookEntityFactory,
    private val bestOfTheYearRepository: BestOfTheYearRepository
) : TestContainerBaseConfiguration() {

    @Test
    fun `get best of the year`() {
        val expected = executeInContext {
            val readingList = (1..12).map {
                val book = bookEntityFactory.buildAllAndSave()
                readingRepository.save(ReadingEntityFactory.build(book))
            }
            val bestOfTheYear = BestOfTheYearEntity(
                year = LocalDate.now().year,
                january = readingList[0],
                february = readingList[1],
                march = readingList[2],
                april = readingList[3],
                may = readingList[4],
                june = readingList[5],
                july = readingList[6],
                august = readingList[7],
                september = readingList[8],
                october = readingList[9],
                november = readingList[10],
                december = readingList[11],
                quarterOne = readingList[1],
                quarterTwo = readingList[3]
            )
            bestOfTheYearRepository.save(bestOfTheYear)
        }

        val response = RestAssured.given()
            .auth()
            .oauth2(jwtToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .`when`()
            .pathParam("year", LocalDate.now().year)
            .get("/bests/{year}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(BestOfTheYearResponse::class.java)

        Assertions.assertEquals(expected.year, response.year)
        Assertions.assertNotNull(response.january?.id)
        Assertions.assertNotNull(response.february?.id)
        Assertions.assertNotNull(response.march?.id)
        Assertions.assertNotNull(response.april?.id)
        Assertions.assertNotNull(response.may?.id)
        Assertions.assertNotNull(response.june?.id)
        Assertions.assertNotNull(response.july?.id)
        Assertions.assertNotNull(response.august?.id)
        Assertions.assertNotNull(response.september?.id)
        Assertions.assertNotNull(response.october?.id)
        Assertions.assertNotNull(response.november?.id)
        Assertions.assertNotNull(response.december?.id)
        Assertions.assertEquals(expected.january?.id, response.january?.id)
        Assertions.assertEquals(expected.february?.id, response.february?.id)
        Assertions.assertEquals(expected.march?.id, response.march?.id)
        Assertions.assertEquals(expected.april?.id, response.april?.id)
        Assertions.assertEquals(expected.may?.id, response.may?.id)
        Assertions.assertEquals(expected.june?.id, response.june?.id)
        Assertions.assertEquals(expected.july?.id, response.july?.id)
        Assertions.assertEquals(expected.august?.id, response.august?.id)
        Assertions.assertEquals(expected.september?.id, response.september?.id)
        Assertions.assertEquals(expected.october?.id, response.october?.id)
        Assertions.assertEquals(expected.november?.id, response.november?.id)
        Assertions.assertEquals(expected.december?.id, response.december?.id)
        Assertions.assertEquals(expected.quarterOne?.id, response.quarterOne?.id)
        Assertions.assertEquals(expected.quarterTwo?.id, response.quarterTwo?.id)
        Assertions.assertNull(response.quarterThree)
        Assertions.assertNull(response.quarterFour)
        Assertions.assertNull(response.bestOfTheYear)
    }
}