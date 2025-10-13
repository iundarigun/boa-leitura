package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.OpenLibraryResponse

@Suppress("MagicNumber")
fun OpenLibraryResponse.toBookInformation(): BookInformation =
    BookInformation(
        title = this.title,
        isbn = if (this.identifiers?.isbn13.isNullOrEmpty()) {
            null
        } else {
            this.identifiers!!.isbn13!![0]
        },
        author = if (this.authors.isEmpty()) {
            null
        } else {
            this.authors[0].name
        },
        numberOfPages = this.numberOfPages,
        publisherYear = runCatching {
            this.publishDate?.substring(this.publishDate.length - 4)?.toInt()
        }.getOrNull(),
        urlImage = this.cover?.large,
        urlImageSmall = this.cover?.small
    )

fun BookInformation.toBookInformationResponse(authorResponse: AuthorResponse?): BookInformationResponse =
    BookInformationResponse(
        title = this.title,
        isbn = this.isbn,
        author = authorResponse,
        numberOfPages = this.numberOfPages,
        urlImage = this.urlImage,
        urlImageSmall = this.urlImageSmall,
        publisherYear = this.publisherYear
    )