package cat.iundarigun.boaleitura.application.port.input.saga

import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface GetSagaByIdUseCase {
    fun execute(id: Long): SagaResponse
}