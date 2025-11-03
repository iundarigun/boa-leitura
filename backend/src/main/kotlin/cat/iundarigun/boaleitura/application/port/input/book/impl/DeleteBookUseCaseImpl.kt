package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.DeleteBookUseCase
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.exception.BookDeleteException
import org.springframework.stereotype.Component

@Component
class DeleteBookUseCaseImpl(private val bookPort: BookPort) : DeleteBookUseCase {
    override fun execute(id: Long) {
        if (bookPort.hasDependencies(id)) {
            throw BookDeleteException(id)
        }
        bookPort.delete(id)
    }
}