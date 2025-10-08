package cat.iundarigun.boaleitura.application.port.input.author.impl

import cat.iundarigun.boaleitura.application.port.input.author.FindAuthorsUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FindAuthorsUseCaseImpl(private val authorPort: AuthorPort) : FindAuthorsUseCase {

    @Transactional(readOnly = true)
    override fun execute(): PageResponse<AuthorResponse> =
        authorPort.find()
}