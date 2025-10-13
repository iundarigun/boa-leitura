package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.SagaBookResponse

fun BookEntity.toSummaryResponse() =
    BookSummaryResponse(
        id = this.id,
        title = this.title,
        author = this.author.name,
        genre = this.genre?.name,
        saga = this.saga?.name,
        urlImageSmall = this.urlImageSmall,
        createdAt = this.createdAt,
        read = this.readings.isNotEmpty()
    )

fun BookEntity.toResponse() =
    BookResponse(
        id = this.id,
        title = this.title,
        author = this.author.toResponse(),
        genre = this.genre?.toResponse(),
        language = this.language,
        numberOfPages = this.numberOfPages ?: 0,
        publisherYear = this.publisherYear,
        originalEdition = this.toOriginalEditionModel(),
        saga = this.toSagaBookResponse(),
        isbn = this.isbn,
        urlImage = this.urlImage,
        urlImageSmall = this.urlImageSmall,
        read = this.readings.isNotEmpty()
    )

fun BookEntity.toOriginalEditionModel(): BookOriginalEditionModel? {
    if (this.originalLanguage.isNullOrBlank() && this.originalTitle.isNullOrBlank()) {
        return null
    }
    return BookOriginalEditionModel(
        title = this.originalTitle,
        language = this.originalLanguage
    )
}

fun BookEntity.toSagaBookResponse(): SagaBookResponse? {
    if (this.saga == null) {
        return null
    }
    return SagaBookResponse(
        saga = this.saga!!.toResponse(),
        order = this.sagaOrder ?: 1.0,
        mainTitle = this.sagaMainTitle ?: true
    )
}

fun BookGoodreadsImporterRequest.toBookEntity(author: AuthorEntity): BookEntity =
    BookEntity(
        goodreadsId = this.goodreadsId,
        title = this.title,
        publisherYear = this.publisherYear,
        numberOfPages = this.numberOfPages,
        isbn = this.isbn,
        originalLanguage = this.originalLanguage,
        author = author
    )