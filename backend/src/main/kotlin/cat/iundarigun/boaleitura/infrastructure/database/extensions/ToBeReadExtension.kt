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
        book = this.book.toSummaryResponse(read = false, inTbr = false),
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

fun ToBeReadEntity.merge(request: ToBeReadRequest, book: BookEntity): ToBeReadEntity {
    this.book = book
    this.position = request.position ?: this.position
    this.bought = request.bought
    this.platforms = request.platforms
    this.tags = request.tags
    this.notes = request.notes

    return this
}