package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity

fun ReadingEntity.toSummaryResponse(): ReadingSummaryResponse =
    ReadingSummaryResponse(
        id = this.id,
        book = this.book.toSummaryResponse(true),
        myRating = this.myRating,
        language = this.language,
        dateRead = this.dateRead
    )

fun ReadingEntity.toResponse(): ReadingResponse =
    ReadingResponse(
        id = this.id,
        book = this.book.toResponse(),
        myRating = this.myRating,
        language = this.language,
        dateRead = this.dateRead
    )

fun ReadingRequest.toReading(book: BookEntity): ReadingEntity =
    ReadingEntity(
        myRating = this.myRating?.toDouble(),
        dateRead = this.dateRead,
        book = book,
        format = this.format,
        platform = this.platform,
        language = this.language
    )