package cat.iundarigun.boaleitura.application.port.input.interactor.author

import cat.iundarigun.boaleitura.application.port.input.DeleteAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.exception.AuthorDeleteException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteAuthorUseCaseImpl(private val authorPort: AuthorPort) : DeleteAuthorUseCase {

    @Transactional
    override fun execute(id: Long) {
        if (authorPort.authorBookCount(id) > 0) {
            throw AuthorDeleteException(id)
        }
        authorPort.delete(id)
    }
}