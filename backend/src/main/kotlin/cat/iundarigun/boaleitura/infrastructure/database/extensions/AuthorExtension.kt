package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse

fun AuthorEntity.merge(request: AuthorRequest): AuthorEntity {
    name = request.name
    gender = request.gender
    nationality = request.nationality
    return this
}

fun AuthorRequest.toEntity(): AuthorEntity =
    AuthorEntity(
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )

fun AuthorEntity.toResponse(): AuthorResponse =
    AuthorResponse(
        id = this.id,
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )