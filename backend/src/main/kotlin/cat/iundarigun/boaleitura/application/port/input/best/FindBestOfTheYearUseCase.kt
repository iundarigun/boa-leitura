package cat.iundarigun.boaleitura.application.port.input.best

import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse

interface FindBestOfTheYearUseCase {

    fun execute(year: Int): BestOfTheYearResponse
}