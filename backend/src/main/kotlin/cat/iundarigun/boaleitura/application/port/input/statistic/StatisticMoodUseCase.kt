package cat.iundarigun.boaleitura.application.port.input.statistic

import cat.iundarigun.boaleitura.domain.response.StatisticMoodResponse

interface StatisticMoodUseCase {
    fun execute(year: Int): StatisticMoodResponse
}