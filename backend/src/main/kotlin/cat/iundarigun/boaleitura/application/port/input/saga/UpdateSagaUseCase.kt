package cat.iundarigun.boaleitura.application.port.input.saga

import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface UpdateSagaUseCase {
    fun execute(id: Long, request: SagaRequest): SagaResponse
}
