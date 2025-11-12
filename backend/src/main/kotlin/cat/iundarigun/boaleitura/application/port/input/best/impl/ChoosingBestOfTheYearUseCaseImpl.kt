package cat.iundarigun.boaleitura.application.port.input.best.impl

import cat.iundarigun.boaleitura.application.port.input.best.ChoosingBestOfTheYearUseCase
import cat.iundarigun.boaleitura.application.port.output.BestOfTheYearPort
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.enums.BestOfTheYearFieldEnum
import cat.iundarigun.boaleitura.domain.extensions.toRequest
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearFieldRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.exception.ReadingNotMatchException
import org.springframework.stereotype.Component

@Component
class ChoosingBestOfTheYearUseCaseImpl(
    private val bestOfTheYearPort: BestOfTheYearPort,
    private val readingPort: ReadingPort
) : ChoosingBestOfTheYearUseCase {

    override fun execute(year: Int, request: BestOfTheYearFieldRequest): BestOfTheYearResponse {
        val bestOfTheYear = bestOfTheYearPort.findByYear(year) ?: BestOfTheYearResponse(year = year)
        val reading = readingPort.findById(request.readingId)

        validateDates(year, reading, request.field)
        validateWinners(reading, request.field, bestOfTheYear)

        return bestOfTheYearPort.save(bestOfTheYear.toRequest(request))
    }

    private fun validateDates(year: Int, reading: ReadingResponse, field: BestOfTheYearFieldEnum) {
        if (reading.dateRead.year != year) {
            throw ReadingNotMatchException()
        }
        if (field != BestOfTheYearFieldEnum.BEST_OF_THE_YEAR && !field.months.contains(reading.dateRead.month)) {
            throw ReadingNotMatchException()
        }
    }

    @Suppress("MagicNumber")
    private fun validateWinners(
        reading: ReadingResponse,
        field: BestOfTheYearFieldEnum,
        bestOfTheYear: BestOfTheYearResponse
    ) {
        val valid = when (field) {
            BestOfTheYearFieldEnum.QUARTER_ONE -> {
                val ids = bestOfTheYear.retrieveQuarterOneIds()
                (ids.size == 3 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.QUARTER_TWO -> {
                val ids = bestOfTheYear.retrieveQuarterTwoIds()
                (ids.size == 3 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.QUARTER_THREE -> {
                val ids = bestOfTheYear.retrieveQuarterThreeIds()
                (ids.size == 3 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.QUARTER_FOUR -> {
                val ids = bestOfTheYear.retrieveQuarterFourIds()
                (ids.size == 3 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.FIRST_HALF -> {
                val ids = bestOfTheYear.retrieveFirstHalfIds()
                (ids.size == 2 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.SECOND_HALF -> {
                val ids = bestOfTheYear.retrieveSecondHalfIds()
                (ids.size == 2 && ids.contains(reading.id))
            }

            BestOfTheYearFieldEnum.BEST_OF_THE_YEAR -> {
                val ids = bestOfTheYear.retrieveBestOfTheYearIds()
                (ids.size == 2 && ids.contains(reading.id))
            }

            else -> true
        }

        if (!valid) {
            throw ReadingNotMatchException()
        }
    }
}