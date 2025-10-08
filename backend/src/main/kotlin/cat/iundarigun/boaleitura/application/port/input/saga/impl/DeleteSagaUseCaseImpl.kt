package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.DeleteSagaUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import org.springframework.stereotype.Component

@Component
class DeleteSagaUseCaseImpl(private val sagaPort: SagaPort) : DeleteSagaUseCase {
    override fun execute(id: Long) {
        TODO()
    }
}