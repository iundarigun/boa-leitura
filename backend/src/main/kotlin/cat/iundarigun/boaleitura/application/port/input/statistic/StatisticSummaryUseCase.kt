package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.request.StatisticsRequest
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse

interface StatisticSummaryUseCase {
    fun execute(request: StatisticsRequest): StatisticSummaryResponse
}