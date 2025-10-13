package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.GetBookByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.response.BookResponse
import org.springframework.stereotype.Component

@Component
class GetBookByIdUseCaseImpl(private val bookPort: BookPort) : GetBookByIdUseCase {

    override fun execute(id: Long): BookResponse =
        bookPort.findById(id)
}