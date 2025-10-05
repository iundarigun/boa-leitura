package cat.iundarigun.boaleitura.application.port.input

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse

interface UpdateAuthorUseCase {
    fun execute(id: Long, request: AuthorRequest): AuthorResponse
}