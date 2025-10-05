package cat.iundarigun.boaleitura.application.port.input.interactor

import cat.iundarigun.boaleitura.application.port.input.GetAuthorByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetAuthorByIdUseCaseImpl(
    private val authorPort: AuthorPort
) : GetAuthorByIdUseCase {

    @Transactional(readOnly = true)
    override fun execute(id: Long): AuthorResponse =
        authorPort.findById(id)
}