package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.AddToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.exception.ToBeReadAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class AddToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : AddToBeReadUseCase {

    override fun execute(request: ToBeReadRequest): ToBeReadResponse {
        if (toBeReadPort.existsByBook(request.bookId)) {
            throw ToBeReadAlreadyExistsException()
        }
        return toBeReadPort.save(request)
    }
}