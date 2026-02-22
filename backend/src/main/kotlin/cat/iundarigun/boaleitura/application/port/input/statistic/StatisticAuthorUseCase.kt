package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticAuthorResponse

interface StatisticAuthorUseCase {
    fun execute(request: StatisticRequest): StatisticAuthorResponse
}