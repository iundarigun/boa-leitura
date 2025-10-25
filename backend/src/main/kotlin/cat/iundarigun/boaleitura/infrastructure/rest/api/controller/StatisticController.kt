package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticAuthorUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticLanguageUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticMoodUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticSummaryUseCase
import cat.iundarigun.boaleitura.domain.response.StatisticAuthorResponse
import cat.iundarigun.boaleitura.domain.response.StatisticLanguageResponse
import cat.iundarigun.boaleitura.domain.response.StatisticMoodResponse
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("statistics")
class StatisticController(
    private val statisticSummaryUseCase: StatisticSummaryUseCase,
    private val statisticLanguageUseCase: StatisticLanguageUseCase,
    private val statisticAuthorUseCase: StatisticAuthorUseCase,
    private val statisticMoodUseCase: StatisticMoodUseCase
) {

    @GetMapping("summary/{year}")
    fun getSummary(@PathVariable year: Int): StatisticSummaryResponse {
        return statisticSummaryUseCase.execute(year)
    }

    @GetMapping("language/{year}")
    fun getLanguage(@PathVariable year: Int): StatisticLanguageResponse {
        return statisticLanguageUseCase.execute(year)
    }

    @GetMapping("author/{year}")
    fun getAuthor(@PathVariable year: Int): StatisticAuthorResponse {
        return statisticAuthorUseCase.execute(year)
    }

    @GetMapping("mood/{year}")
    fun getMood(@PathVariable year: Int): StatisticMoodResponse {
        return statisticMoodUseCase.execute(year)
    }
}