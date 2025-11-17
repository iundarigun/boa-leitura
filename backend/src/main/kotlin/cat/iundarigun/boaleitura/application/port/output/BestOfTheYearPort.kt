package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.BestOfTheYearRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse

interface BestOfTheYearPort {
    fun findByYear(year: Int): BestOfTheYearResponse?
    fun save(request: BestOfTheYearRequest): BestOfTheYearResponse
}