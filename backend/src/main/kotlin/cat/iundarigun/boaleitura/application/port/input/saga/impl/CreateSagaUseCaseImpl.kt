package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.CreateSagaUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.exception.SagaAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class CreateSagaUseCaseImpl(private val sagaPort: SagaPort) : CreateSagaUseCase {

    override fun execute(request: SagaRequest): SagaResponse {
        if (sagaPort.existsByName(request.name)) {
            throw SagaAlreadyExistsException(request.name)
        }
        return sagaPort.save(request)
    }
}