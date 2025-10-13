package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.GoogleApiItemResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.OpenLibraryResponse

fun OpenLibraryResponse.toBookInformation(googleApiItem: GoogleApiItemResponse? = null): BookInformation =
    BookInformation(
        title = this.title,
        isbn = this.getIsbn() ?: googleApiItem?.getIsbn(),
        author = this.authors.firstOrNull()?.name ?: googleApiItem?.volumeInfo?.authors?.firstOrNull(),
        numberOfPages = if ((this.numberOfPages ?: 0) > 0) this.numberOfPages else googleApiItem?.volumeInfo?.pageCount,
        publisherYear = this.getPublisherYear() ?: googleApiItem?.getPublisherYear(),
        urlImage = this.cover?.large ?: googleApiItem?.volumeInfo?.imageLinks?.thumbnail,
        urlImageSmall = this.cover?.small ?: googleApiItem?.volumeInfo?.imageLinks?.smallThumbnail
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

fun GoogleApiItemResponse.toBookInformation(): BookInformation =
    BookInformation(
        title = this.volumeInfo.title,
        author = this.volumeInfo.authors.firstOrNull(),
        numberOfPages = this.volumeInfo.pageCount,
        isbn = this.getIsbn(),
        publisherYear = this.getPublisherYear(),
        urlImage = this.volumeInfo.imageLinks?.thumbnail,
        urlImageSmall = this.volumeInfo.imageLinks?.smallThumbnail
    )