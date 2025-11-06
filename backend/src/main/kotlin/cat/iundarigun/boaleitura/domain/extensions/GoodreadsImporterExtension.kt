package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.exception.GoodreadsImporterException

fun GoodreadsImporterRequest.toBookRequest(userPreferences: UserPreferences): BookGoodreadsImporterRequest =
    BookGoodreadsImporterRequest(
        goodreadsId = this.bookId,
        title = this.title,
        numberOfPages = this.numberOfPages,
        publisherYear = this.yearPublished,
        isbn = if (this.isbn13 == "=\"\"") {
            null
        } else {
            (this.isbn13.replace("=\"(.*)\"".toRegex(), "$1"))
        },
        language = retrieveFromBookshelves(this.bookshelves, userPreferences.languageTags)
    )

fun GoodreadsImporterRequest.toReadingRequest(bookId: Long, userPreferences: UserPreferences): ReadingRequest =
    ReadingRequest(
        bookId = bookId,
        myRating = this.myRating?.toDouble(),
        dateRead = this.dateRead ?: throw GoodreadsImporterException("Date not found"),
        format = FormatEnum.findValue(retrieveFromBookshelves(this.bookshelves, userPreferences.formatTags)),
        platform = PlatformEnum.findValue(retrieveFromBookshelves(this.bookshelves, userPreferences.platformTags)),
        language = retrieveFromBookshelves(this.bookshelves, userPreferences.languageTags)
    )

private fun retrieveFromBookshelves(bookshelves: List<String>, languageUserPreferences: Map<String, String>): String? =
    bookshelves.firstNotNullOfOrNull { languageUserPreferences[it] }