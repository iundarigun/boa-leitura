package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticAuthorUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticLanguageUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticMoodUseCase
import cat.iundarigun.boaleitura.application.port.input.statistic.StatisticSummaryUseCase
import cat.iundarigun.boaleitura.domain.request.StatisticsRequest
import cat.iundarigun.boaleitura.domain.response.StatisticAuthorResponse
import cat.iundarigun.boaleitura.domain.response.StatisticLanguageResponse
import cat.iundarigun.boaleitura.domain.response.StatisticMoodResponse
import cat.iundarigun.boaleitura.domain.response.StatisticSummaryResponse
import jakarta.validation.Valid
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

    @GetMapping("summary")
    fun getSummary(@Valid request: StatisticsRequest): StatisticSummaryResponse {
        return statisticSummaryUseCase.execute(request)
    }

    @GetMapping("language")
    fun getLanguage(@Valid request: StatisticsRequest): StatisticLanguageResponse {
        return statisticLanguageUseCase.execute(request)
    }

    @GetMapping("author")
    fun getAuthor(@Valid request: StatisticsRequest): StatisticAuthorResponse {
        return statisticAuthorUseCase.execute(request)
    }

    @GetMapping("mood")
    fun getMood(@Valid request: StatisticsRequest): StatisticMoodResponse {
        return statisticMoodUseCase.execute(request)
    }
}