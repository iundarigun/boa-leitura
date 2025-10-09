package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.FindSagasUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SearchSagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import org.springframework.stereotype.Component

@Component
class FindSagasUseCaseImpl(private val sagaPort: SagaPort) : FindSagasUseCase {

    override fun execute(request: SearchSagaRequest): PageResponse<SagaResponse> =
        sagaPort.find(request.name, request.toPageRequest())
}