package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.entity.Author
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse

fun Author.merge(request: AuthorRequest): Author {
    name = request.name
    gender = request.gender
    nationality = request.nationality
    return this
}

fun AuthorRequest.toEntity(): Author =
    Author(
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )

fun Author.toResponse(): AuthorResponse =
    AuthorResponse(
        id = this.id,
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )