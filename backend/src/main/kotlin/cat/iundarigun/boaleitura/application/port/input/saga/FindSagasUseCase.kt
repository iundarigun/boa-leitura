package cat.iundarigun.boaleitura.application.port.input.saga

import cat.iundarigun.boaleitura.domain.request.SearchSagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface FindSagasUseCase {
    fun execute(request: SearchSagaRequest): PageResponse<SagaResponse>
}