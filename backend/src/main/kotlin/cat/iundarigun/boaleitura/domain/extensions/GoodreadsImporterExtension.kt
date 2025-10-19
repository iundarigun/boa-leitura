package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.LanguageEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import cat.iundarigun.boaleitura.domain.enums.TagEnum
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
        format = this.toFormat(),
        platform = this.toPlatform(),
        language = LanguageEnum.findValue(this.bookshelves)?.value
    )

private fun GoodreadsImporterRequest.toFormat(): FormatEnum? {
    val format = TagEnum.findValue(this.bookshelves)
    return format?.let {
        when (format) {
            TagEnum.FISIC -> FormatEnum.PRINTED
            TagEnum.AUDIOBOOK -> FormatEnum.AUDIOBOOK
            else -> FormatEnum.EBOOK
        }
    }
}

private fun GoodreadsImporterRequest.toPlatform(): PlatformEnum? {
    val format = TagEnum.findValue(this.bookshelves)
    return format?.let {
        val language = LanguageEnum.findValue(this.bookshelves)?.value
        when (format) {
            TagEnum.AUDIOBOOK -> PlatformEnum.AUDIBLE
            TagEnum.KINDLE -> PlatformEnum.KINDLE
            TagEnum.UNLIMITED -> PlatformEnum.UNLIMITED
            TagEnum.BIBLIO -> if (language == "pt") PlatformEnum.BIBLION else PlatformEnum.EBIBLIO
            else -> PlatformEnum.OWN
        }
    }
}
