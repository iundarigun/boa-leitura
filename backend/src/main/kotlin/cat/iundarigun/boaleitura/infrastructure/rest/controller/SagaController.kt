package cat.iundarigun.boaleitura.infrastructure.rest.controller

import cat.iundarigun.boaleitura.application.port.input.saga.CreateSagaUseCase
import cat.iundarigun.boaleitura.application.port.input.saga.DeleteSagaUseCase
import cat.iundarigun.boaleitura.application.port.input.saga.FindSagasUseCase
import cat.iundarigun.boaleitura.application.port.input.saga.GetSagaByIdUseCase
import cat.iundarigun.boaleitura.application.port.input.saga.UpdateSagaUseCase
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.request.SearchSagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
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
@RequestMapping("/sagas")
class SagaController(
    private val findSagaUseCase: FindSagasUseCase,
    private val createSagaUseCase: CreateSagaUseCase,
    private val updateSagaUseCase: UpdateSagaUseCase,
    private val getSagaByIdUseCase: GetSagaByIdUseCase,
    private val deleteSagaUseCase: DeleteSagaUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getSagas(request: SearchSagaRequest): PageResponse<SagaResponse> {
        logger.info("getSagas, request=$request")
        return findSagaUseCase.execute(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSagaById(@PathVariable id: Long): SagaResponse {
        logger.info("getSagaById, id=$id")
        return getSagaByIdUseCase.execute(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSaga(@Valid @RequestBody request: SagaRequest): SagaResponse {
        logger.info("createSaga, request=$request")
        return createSagaUseCase.execute(request)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateSaga(@PathVariable id: Long, @Valid @RequestBody request: SagaRequest): SagaResponse {
        logger.info("updateSaga, id=$id, request=$request")
        return updateSagaUseCase.execute(id, request)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) {
        logger.info("deleteById, id=$id")
        deleteSagaUseCase.execute(id)
    }
}