package cat.iundarigun.boaleitura.application.port.input.best

import cat.iundarigun.boaleitura.domain.request.BestOfTheYearFieldRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse

interface ChoosingBestOfTheYearUseCase {

    fun execute(year: Int, request: BestOfTheYearFieldRequest): BestOfTheYearResponse
}