package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.UpdateToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.exception.ToBeReadAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class UpdateToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : UpdateToBeReadUseCase {

    override fun execute(id: Long, request: ToBeReadRequest): ToBeReadResponse {
        val tbr = toBeReadPort.findByBook(request.bookId)
        tbr?.let {
            if (it.id != id) {
                throw ToBeReadAlreadyExistsException()
            }
        }
        return toBeReadPort.save(request, id)
    }
}