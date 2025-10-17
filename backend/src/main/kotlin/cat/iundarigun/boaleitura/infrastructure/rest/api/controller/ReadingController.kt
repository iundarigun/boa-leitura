package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.reading.FindReadingUseCase
import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reading")
class ReadingController(
    private val findReadingUseCase: FindReadingUseCase,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getReading(@Valid request: SearchReadingRequest): PageResponse<ReadingResponse> {
        logger.info("getReading, request=$request")
        return findReadingUseCase.execute(request)
    }
}