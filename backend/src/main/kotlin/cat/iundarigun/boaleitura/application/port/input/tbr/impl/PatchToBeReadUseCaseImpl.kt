package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.PatchToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.exception.ToBeReadFieldsNotAllowedException
import org.springframework.stereotype.Component

@Component
class PatchToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : PatchToBeReadUseCase {

    override fun execute(id: Long, request: Map<String, Any>) {
        if (request.keys.any { !allowedFields.contains(it) }) {
            throw ToBeReadFieldsNotAllowedException()
        }
        toBeReadPort.update(id, request)
    }

    companion object {
        val allowedFields = listOf("bought", "done")
    }
}