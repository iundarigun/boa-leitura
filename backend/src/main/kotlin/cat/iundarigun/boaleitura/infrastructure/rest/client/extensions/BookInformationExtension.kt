package cat.iundarigun.boaleitura.infrastructure.rest.client.extensions

import cat.iundarigun.boaleitura.domain.model.BookInformation
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.googleapi.GoogleApiItemResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary.BookResponse

fun BookResponse.toBookInformation(googleApiItem: GoogleApiItemResponse? = null): BookInformation =
    BookInformation(
        title = this.title,
        isbn = this.getIsbn() ?: googleApiItem?.getIsbn(),
        author = this.authors.firstOrNull()?.name ?: googleApiItem?.volumeInfo?.authors?.firstOrNull(),
        numberOfPages = if ((this.numberOfPages ?: 0) > 0) this.numberOfPages else googleApiItem?.volumeInfo?.pageCount,
        publisherYear = this.getPublisherYear() ?: googleApiItem?.getPublisherYear(),
        urlImage = this.cover?.large ?: googleApiItem?.volumeInfo?.imageLinks?.thumbnail,
        urlImageSmall = this.cover?.small ?: googleApiItem?.volumeInfo?.imageLinks?.smallThumbnail
    )

fun GoogleApiItemResponse.toBookInformation(): BookInformation =
    BookInformation(
        title = this.volumeInfo.getFullTitle(),
        author = this.volumeInfo.authors.firstOrNull(),
        numberOfPages = this.volumeInfo.pageCount,
        isbn = this.getIsbn(),
        publisherYear = this.getPublisherYear(),
        urlImage = this.volumeInfo.imageLinks?.thumbnail,
        urlImageSmall = this.volumeInfo.imageLinks?.smallThumbnail
    )