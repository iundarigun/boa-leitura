package cat.iundarigun.boaleitura.application.port.input

import cat.iundarigun.boaleitura.domain.response.AuthorResponse

interface GetAuthorByIdUseCase {
    fun execute(id: Long): AuthorResponse
}