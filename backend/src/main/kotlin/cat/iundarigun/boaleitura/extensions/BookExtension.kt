package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse

fun BookEntity.toBookSummaryResponse() =
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