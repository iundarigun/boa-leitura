package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.request.StatisticRequest
import cat.iundarigun.boaleitura.domain.response.StatisticLanguageResponse

interface StatisticLanguageUseCase {
    fun execute(request: StatisticRequest): StatisticLanguageResponse
}