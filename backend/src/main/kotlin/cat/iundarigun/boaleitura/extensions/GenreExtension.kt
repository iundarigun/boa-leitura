package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.response.GenreResponse

fun GenreEntity.toResponse() : GenreResponse =
    GenreResponse(
        id = this.id,
        name = this.name,
        parent = this.parent?.toResponse()
    )
