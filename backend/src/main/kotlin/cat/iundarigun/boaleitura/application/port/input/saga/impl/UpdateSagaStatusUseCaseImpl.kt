package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.UpdateSagaStatusUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.enums.SagaStatusEnum
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.exception.SagaAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class UpdateSagaStatusUseCaseImpl(private val sagaPort: SagaPort) : UpdateSagaStatusUseCase {

    override fun execute(id: Long, request: SagaStatusEnum): SagaResponse {
        sagaPort.findByName(request.name)?.let {
            if (it.id != id) {
                throw SagaAlreadyExistsException(request.name)
            }
        }
        return sagaPort.updateSagaStatus(id, request)
    }
}
