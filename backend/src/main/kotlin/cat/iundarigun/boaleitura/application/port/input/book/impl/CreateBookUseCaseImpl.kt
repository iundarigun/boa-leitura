package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.CreateBookUseCase
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import org.springframework.stereotype.Component

@Component
class CreateBookUseCaseImpl(
    private val bookPort: BookPort,
    private val bookValidator: BookValidator
) : CreateBookUseCase {
    override fun execute(request: BookRequest): BookResponse {
        bookValidator.validate(request)

        return bookPort.save(request)
    }
}