package cat.iundarigun.boaleitura.application.port.input.saga.impl

import cat.iundarigun.boaleitura.application.port.input.saga.GetSagaByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import org.springframework.stereotype.Component

@Component
class GetSagaByIdUseCaseImpl(private val sagaPort: SagaPort) : GetSagaByIdUseCase {

    override fun execute(id: Long): SagaResponse =
        sagaPort.findById(id)
}