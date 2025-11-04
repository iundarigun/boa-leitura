package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.DeleteToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import org.springframework.stereotype.Component

@Component
class DeleteToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : DeleteToBeReadUseCase {

    override fun execute(id: Long) {
        toBeReadPort.delete(id)
    }
}