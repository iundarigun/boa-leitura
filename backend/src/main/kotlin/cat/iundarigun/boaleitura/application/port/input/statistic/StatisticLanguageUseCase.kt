package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.response.StatisticLanguageResponse

interface StatisticLanguageUseCase {
    fun execute(year: Int): StatisticLanguageResponse
}