package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticMoodResponse

interface StatisticMoodUseCase {
    fun execute(request: StatisticRequest): StatisticMoodResponse
}