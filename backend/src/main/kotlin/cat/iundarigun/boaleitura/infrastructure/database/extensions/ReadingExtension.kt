package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingBookResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import org.springframework.data.convert.ConverterBuilder.reading

fun ReadingEntity.toSummaryResponse(): ReadingSummaryResponse =
    ReadingSummaryResponse(
        id = this.id,
        book = this.book.toSummaryResponse(true, false),
        myRating = this.myRating,
        language = this.language,
        dateRead = this.dateRead
    )

fun ReadingEntity.toResponse(positionInYear: Int = 0): ReadingResponse =
    ReadingResponse(
        id = this.id,
        book = this.book.toResponse(),
        myRating = this.myRating,
        language = this.language,
        dateRead = this.dateRead,
        format = this.format,
        platform = this.platform,
        positionInYear = positionInYear
    )

fun ReadingEntity.toReadingBookResponse(): ReadingBookResponse =
    ReadingBookResponse(
        id = this.id,
        myRating = this.myRating,
        language = this.language,
        dateRead = this.dateRead,
        format = this.format,
        platform = this.platform
    )

fun ReadingRequest.toReading(book: BookEntity, rereading: Boolean): ReadingEntity =
    ReadingEntity(
        myRating = this.myRating,
        dateRead = this.dateRead,
        book = book,
        format = this.format,
        platform = this.platform,
        language = this.language,
        rereading = rereading,
    )

fun ReadingEntity.merge(request: ReadingRequest, rereading: Boolean): ReadingEntity {
    this.dateRead = request.dateRead
    this.myRating = request.myRating
    this.language = request.language
    this.format = request.format
    this.platform = request.platform
    this.rereading = rereading
    return this
}