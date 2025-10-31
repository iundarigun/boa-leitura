package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ToBeReadEntity

fun ToBeReadEntity.toResponse(): ToBeReadResponse =
    ToBeReadResponse(
        id = this.id,
        position = this.position,
        bought = this.bought,
        done = this.done,
        book = this.book.toSummaryResponse(false),
        tags = this.tags,
        platforms = this.platforms,
        notes = this.notes,
        addedAt = this.createdAt.toLocalDate()
    )

fun ToBeReadRequest.toEntity(book: BookEntity, position: Long): ToBeReadEntity =
    ToBeReadEntity(
        position = position,
        book = book,
        bought = this.bought,
        platforms = this.platforms,
        tags = this.tags,
        notes = this.notes,
    )