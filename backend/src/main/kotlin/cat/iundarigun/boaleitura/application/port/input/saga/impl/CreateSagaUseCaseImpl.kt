package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.CreateSagaUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import org.springframework.stereotype.Component

@Component
class CreateSagaUseCaseImpl(private val sagaPort: SagaPort) : CreateSagaUseCase {
    override fun execute(request: SagaRequest): SagaResponse {
        TODO("Not yet implemented")
    }
}