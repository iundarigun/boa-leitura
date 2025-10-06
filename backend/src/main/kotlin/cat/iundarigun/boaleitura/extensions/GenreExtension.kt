package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse

fun GenreEntity.toResponse(): GenreResponse =
    GenreResponse(
        id = this.id,
        name = this.name,
        parent = this.parent?.toResponse()
    )

fun GenreEntity.toResponseWithSubGenders(): GenreResponse =
    GenreResponse(
        id = this.id,
        name = this.name,
        subGenres = this.subGenres.map { it.toResponseWithSubGenders() }
    )

fun GenreRequest.toEntity(parent: GenreEntity? = null): GenreEntity =
    GenreEntity(
        name = this.name,
        parent = parent
    )

fun GenreEntity.merge(request: GenreRequest, parent: GenreEntity? = null): GenreEntity {
    this.name = request.name
    this.parent = parent
    return this
}