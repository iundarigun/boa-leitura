package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.UpdateBookUseCase
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import org.springframework.stereotype.Component

@Component
class UpdateBookUseCaseImpl(
    private val bookPort: BookPort,
    private val bookValidator: BookValidator
) : UpdateBookUseCase {
    override fun execute(id: Long, request: BookRequest): BookResponse {
        bookValidator.validate(request, id)

        return bookPort.save(request, id)
    }
}