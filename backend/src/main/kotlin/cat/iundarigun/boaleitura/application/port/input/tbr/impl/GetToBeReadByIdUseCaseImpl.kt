package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.GetToBeReadByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import org.springframework.stereotype.Component

@Component
class GetToBeReadByIdUseCaseImpl(private val toBeReadPort: ToBeReadPort) : GetToBeReadByIdUseCase {

    override fun execute(id: Long): ToBeReadResponse =
        toBeReadPort.findById(id)
}