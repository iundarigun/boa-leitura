package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.DeleteSagaUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.exception.SagaDeleteException
import org.springframework.stereotype.Component

@Component
class DeleteSagaUseCaseImpl(private val sagaPort: SagaPort) : DeleteSagaUseCase {

    override fun execute(id: Long) {
        if (sagaPort.sagaBookCount(id) > 0) {
            throw SagaDeleteException(id)
        }
        sagaPort.delete(id)
    }
}