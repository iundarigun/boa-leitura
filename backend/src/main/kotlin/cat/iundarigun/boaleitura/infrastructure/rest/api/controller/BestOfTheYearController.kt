package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.best.FindBestOfTheYearUseCase
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearFieldRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("bests")
class BestOfTheYearController(
    private val findBestOfTheYearUseCase: FindBestOfTheYearUseCase
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("{year}")
    fun getBestOfTheYear(@PathVariable("year") year: Int): BestOfTheYearResponse {
        logger.info("getBestOfTheYear, year=$year")
        return findBestOfTheYearUseCase.execute(year)
    }

    @PatchMapping("{year}")
    fun patchBestOfTheYear(
        @PathVariable("year") year: Int,
        @RequestBody request: BestOfTheYearFieldRequest
    ): BestOfTheYearResponse {
        TODO()
    }
}