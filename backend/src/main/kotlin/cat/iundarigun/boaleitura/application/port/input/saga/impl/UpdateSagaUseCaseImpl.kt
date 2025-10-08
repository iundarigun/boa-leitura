package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.UpdateSagaUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import org.springframework.stereotype.Component

@Component
class UpdateSagaUseCaseImpl(private val sagaPort: SagaPort) : UpdateSagaUseCase {
    override fun execute(id: Long, request: SagaRequest): SagaResponse {
       TODO()
    }
}
