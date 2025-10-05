package cat.iundarigun.boaleitura.application.port.input.interactor.author

import cat.iundarigun.boaleitura.application.port.input.CreateAuthorUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.exception.AuthorAlreadyExistsException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateAuthorUseCaseImpl(private val authorPort: AuthorPort) : CreateAuthorUseCase {

    @Transactional
    override fun execute(request: AuthorRequest): AuthorResponse {
        if (authorPort.existsByName(request.name)) {
            throw AuthorAlreadyExistsException(request.name)
        }
        return authorPort.save(request)
    }
}