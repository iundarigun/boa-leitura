package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.exception.GoodreadsImporterException
import kotlin.enums.EnumEntries

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
        format = FormatEnum.entries.findValue(retrieveFromBookshelves(this.bookshelves, userPreferences.formatTags)),
        platform = PlatformEnum.entries.findValue(
            retrieveFromBookshelves(
                this.bookshelves,
                userPreferences.platformTags
            )
        ),
        language = retrieveFromBookshelves(this.bookshelves, userPreferences.languageTags)
    )

fun GoodreadsImporterRequest.toBeReadRequest(bookId: Long): ToBeReadRequest =
    ToBeReadRequest(
        bookId = bookId,
        tags = this.bookshelves
    )

private fun retrieveFromBookshelves(bookshelves: List<String>, languageUserPreferences: Map<String, String>): String? =
    bookshelves.firstNotNullOfOrNull { languageUserPreferences[it] }

fun <T : Enum<T>> EnumEntries<T>.findValue(value: String?): T? {
    if (value.isNullOrBlank()) {
        return null
    }
    return this.find { it.name == value }
}