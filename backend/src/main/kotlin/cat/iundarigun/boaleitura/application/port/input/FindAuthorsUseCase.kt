package cat.iundarigun.boaleitura.application.port.input

import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface FindAuthorsUseCase {
    fun execute(): PageResponse<AuthorResponse>
}