package cat.iundarigun.boaleitura.application.port.input.author.impl

import cat.iundarigun.boaleitura.application.port.input.author.UpdateAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.exception.AuthorAlreadyExistsException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateAuthorUseCaseImpl(private val authorPort: AuthorPort) : UpdateAuthorUseCase {

    @Transactional
    override fun execute(id: Long, request: AuthorRequest): AuthorResponse {
        authorPort.findByName(request.name)?.let {
            if (it.id != id) {
                throw AuthorAlreadyExistsException(request.name)
            }
        }
        return authorPort.save(request, id)
    }
}