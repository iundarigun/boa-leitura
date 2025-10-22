package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticSummaryUseCase
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("statistics")
class StatisticController(
    private val statisticSummaryUseCase: StatisticSummaryUseCase
) {

    @GetMapping("summary/{year}")
    fun getSummary(@PathVariable year: Int): StatisticSummaryResponse {
        return statisticSummaryUseCase.execute(year)
    }
}