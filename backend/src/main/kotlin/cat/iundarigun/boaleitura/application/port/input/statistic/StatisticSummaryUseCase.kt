package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse

interface StatisticSummaryUseCase {
    fun execute(request: StatisticRequest): StatisticSummaryResponse
}