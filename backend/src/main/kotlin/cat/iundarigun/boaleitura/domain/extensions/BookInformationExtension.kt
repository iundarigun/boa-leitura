package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse

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