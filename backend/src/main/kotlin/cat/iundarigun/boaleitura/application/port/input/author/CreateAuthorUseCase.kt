package cat.iundarigun.boaleitura.application.port.input.author

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse

interface CreateAuthorUseCase {
    fun execute(request: AuthorRequest): AuthorResponse
}