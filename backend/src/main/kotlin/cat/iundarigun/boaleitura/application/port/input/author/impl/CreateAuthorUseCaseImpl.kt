package cat.iundarigun.boaleitura.application.port.input.author.impl

import cat.iundarigun.boaleitura.application.port.input.author.CreateAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.exception.AuthorAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class CreateAuthorUseCaseImpl(private val authorPort: AuthorPort) : CreateAuthorUseCase {

    override fun execute(request: AuthorRequest): AuthorResponse {
        if (authorPort.existsByName(request.name)) {
            throw AuthorAlreadyExistsException(request.name)
        }
        return authorPort.save(request)
    }
}