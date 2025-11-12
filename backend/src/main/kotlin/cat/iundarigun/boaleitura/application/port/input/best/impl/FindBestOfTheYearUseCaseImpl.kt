package cat.iundarigun.boaleitura.application.port.input.best.impl

import cat.iundarigun.boaleitura.application.port.input.best.FindBestOfTheYearUseCase
import cat.iundarigun.boaleitura.application.port.output.BestOfTheYearPort
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import org.springframework.stereotype.Component

@Component
class FindBestOfTheYearUseCaseImpl(private val bestOfTheYearPort: BestOfTheYearPort) : FindBestOfTheYearUseCase {

    override fun execute(year: Int): BestOfTheYearResponse =
        bestOfTheYearPort.findByYear(year) ?: BestOfTheYearResponse(year = year)
}