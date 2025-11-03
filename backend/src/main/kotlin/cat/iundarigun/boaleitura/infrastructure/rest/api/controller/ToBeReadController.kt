package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.tbr.AddToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.input.tbr.FindToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.input.tbr.ReorderToBeReadUseCase
import cat.iundarigun.boaleitura.domain.request.SearchToBeReadRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadReorderRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tbr")
class ToBeReadController(
    private val findToBeReadUseCase: FindToBeReadUseCase,
    private val addToBeReadUseCase: AddToBeReadUseCase,
    private val reorderToBeReadUseCase: ReorderToBeReadUseCase,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getToBeReads(@Valid request: SearchToBeReadRequest): PageResponse<ToBeReadResponse> {
        logger.info("getReading, request=$request")
        return findToBeReadUseCase.execute(request)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createToBeRead(@Valid @RequestBody request: ToBeReadRequest): ToBeReadResponse {
        logger.info("createToBeRead, request=$request")
        return addToBeReadUseCase.execute(request)
    }

    @PatchMapping("{id}/reorder")
    fun reorderToBeRead(@PathVariable id: Long, @RequestBody request: ToBeReadReorderRequest) {
        logger.info("reorderToBeRead, id=$id, request=$request")
        return reorderToBeReadUseCase.execute(id, request)
    }
}