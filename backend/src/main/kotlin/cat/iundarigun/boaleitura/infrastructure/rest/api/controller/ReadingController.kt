package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.reading.FindReadingsUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.GetReadingByIdUseCase
import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/readings")
class ReadingController(
    private val findReadingsUseCase: FindReadingsUseCase,
    private val getReadingByIdUseCase: GetReadingByIdUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getReadings(@Valid request: SearchReadingRequest): PageResponse<ReadingSummaryResponse> {
        logger.info("getReading, request=$request")
        return findReadingsUseCase.execute(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getReadingById(@PathVariable id: Long): ReadingResponse {
        logger.info("getReadingById, id=$id")
        return getReadingByIdUseCase.execute(id)
    }
}