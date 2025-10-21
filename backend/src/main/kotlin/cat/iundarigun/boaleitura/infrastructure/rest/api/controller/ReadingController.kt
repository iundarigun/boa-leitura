package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.reading.CreateReadingUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.DeleteReadingUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.FindReadingsUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.GetReadingByIdUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.UpdateReadingUseCase
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/readings")
class ReadingController(
    private val findReadingsUseCase: FindReadingsUseCase,
    private val getReadingByIdUseCase: GetReadingByIdUseCase,
    private val deleteReadingUseCase: DeleteReadingUseCase,
    private val createReadingUseCase: CreateReadingUseCase,
    private val updateReadingUseCase: UpdateReadingUseCase,
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createReading(@Valid @RequestBody request: ReadingRequest): ReadingResponse {
        logger.info("createReading, request=$request")
        return createReadingUseCase.execute(request)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateReading(@PathVariable id: Long, @Valid @RequestBody request: ReadingRequest): ReadingResponse {
        logger.info("updateReading, id=$id, request=$request")
        return updateReadingUseCase.execute(id, request)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReading(@PathVariable id: Long) {
        logger.info("deleteReading, id=$id")
        deleteReadingUseCase.execute(id)
    }
}