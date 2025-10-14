package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.entity.SagaEntity
import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.BookRequest
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

fun BookRequest.toEntity(
    author: AuthorEntity,
    genre: GenreEntity,
    saga: SagaEntity?
): BookEntity =
    BookEntity(
        title = this.title,
        author = author,
        genre = genre,
        language = this.language,
        numberOfPages = this.numberOfPages,
        publisherYear = this.publisherYear,
        originalTitle = this.originalEdition?.title,
        originalLanguage = this.originalEdition?.language,
        saga = saga,
        sagaOrder = this.saga?.order,
        sagaMainTitle = this.saga?.mainTitle,
        isbn = this.isbn,
        urlImage = this.urlImage,
        urlImageSmall = this.urlImageSmall
    )

fun BookEntity.merge(request: BookRequest, author: AuthorEntity, genre: GenreEntity, saga: SagaEntity?): BookEntity {
    this.title = request.title
    this.author = author
    this.genre = genre
    this.language = request.language
    this.numberOfPages = request.numberOfPages
    this.publisherYear = request.publisherYear
    this.originalTitle = request.originalEdition?.title
    this.originalLanguage = request.originalEdition?.language
    this.saga = saga
    this.sagaOrder = request.saga?.order
    this.sagaMainTitle = request.saga?.mainTitle
    this.isbn = request.isbn
    this.urlImage = request.urlImage
    this.urlImageSmall = request.urlImageSmall
    return this
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