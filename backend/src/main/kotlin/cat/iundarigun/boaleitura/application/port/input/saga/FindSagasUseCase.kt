package cat.iundarigun.boaleitura.application.port.input.saga

import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface FindSagasUseCase {
    fun execute(): PageResponse<SagaResponse>
}