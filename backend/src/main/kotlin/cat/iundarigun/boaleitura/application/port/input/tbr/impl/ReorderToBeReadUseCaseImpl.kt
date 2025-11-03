package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.ReorderToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.ToBeReadReorderRequest
import org.springframework.stereotype.Component

@Component
class ReorderToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : ReorderToBeReadUseCase {

    override fun execute(id: Long, request: ToBeReadReorderRequest) {
        toBeReadPort.reorder(id, request)
    }
}