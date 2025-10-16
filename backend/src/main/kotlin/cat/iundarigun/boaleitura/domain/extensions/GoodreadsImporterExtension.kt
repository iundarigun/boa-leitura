package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.LanguageEnum
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.exception.GoodreadsImporterException

fun GoodreadsImporterRequest.toBookRequest(): BookGoodreadsImporterRequest =
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
        language = LanguageEnum.findValue(this.bookshelves)?.value
    )

fun GoodreadsImporterRequest.toReadingRequest(bookId: Long): ReadingRequest =
    ReadingRequest(
        bookId = bookId,
        myRating = this.myRating,
        dateRead = this.dateRead ?: throw GoodreadsImporterException("Date not found"),
        format = FormatEnum.findValue(this.bookshelves),
        language = LanguageEnum.findValue(this.bookshelves)?.value
    )
