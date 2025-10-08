package cat.iundarigun.boaleitura.application.port.input.author.impl

import cat.iundarigun.boaleitura.application.port.input.author.DeleteAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.exception.AuthorDeleteException
import org.springframework.stereotype.Component

@Component
class DeleteAuthorUseCaseImpl(private val authorPort: AuthorPort) : DeleteAuthorUseCase {

    override fun execute(id: Long) {
        if (authorPort.authorBookCount(id) > 0) {
            throw AuthorDeleteException(id)
        }
        authorPort.delete(id)
    }
}