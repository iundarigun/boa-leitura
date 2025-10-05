package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.LanguageEnum
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.request.toGoodreadImporterRequest
import cat.iundarigun.boaleitura.exception.GoodreadsImporterException
import com.opencsv.CSVReader
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileReader

@Service
class ImportService(
    private val authorService: AuthorAdapter,
    private val bookService: BookService,
    private val readingService: ReadingService
) {

    fun import(file: String): List<GoodreadsImporterRequest> {
        val records = CSVReader(FileReader(File(file))).readAll()

        val goodreadsList = records.subList(1, records.size).map { it ->
            it.toGoodreadImporterRequest()
        }
        goodreadsList.filter { it.dateRead != null }.forEach {
            importRecord(it)
        }
        return goodreadsList
    }

    private fun importRecord(record: GoodreadsImporterRequest) {
        val author = authorService.createIfNotExists(record.author)
        val book = bookService.createIfNotExists(record.toBookRequest(), author)
        readingService.createIfNotExists(record.toReadingRequest(), book)
    }
}

private fun GoodreadsImporterRequest.toBookRequest(): BookRequest =
    BookRequest(
        goodreadsId = this.bookId,
        title = this.title,
        numberOfPages = this.numberOfPages,
        publisherYear = this.yearPublished,
        isbn = this.isbn,
        isbn13 = this.isbn13
    )

private fun GoodreadsImporterRequest.toReadingRequest(): ReadingRequest =
    ReadingRequest(
        myRating = this.myRating,
        dateRead = this.dateRead ?: throw GoodreadsImporterException("Date not found"),
        format = FormatEnum.findValue(this.bookshelves),
        language = LanguageEnum.findValue(this.bookshelves)?.label
    )