package cat.iundarigun.boaleitura.application.port.input.goodreads.impl

import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.extensions.toBookRequest
import cat.iundarigun.boaleitura.domain.extensions.toReadingRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.toGoodreadImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import com.opencsv.CSVReader
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader

@Component
class GoodreadsImportUseCaseImpl(
    private val authorPort: AuthorPort,
    private val bookPort: BookPort,
    private val readingAdapter: ReadingPort,
    private val jobScheduler: JobScheduler
) : GoodreadsImportUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute(file: String): List<GoodreadsImporterRequest> {
        val records = CSVReader(FileReader(File(file))).readAll()

        val goodreadsList = records.subList(1, records.size).map { it ->
            it.toGoodreadImporterRequest()
        }
        goodreadsList.filter { it.dateRead != null }.forEach {
            jobScheduler.enqueue {
                importRecord(it)
            }
        }
        return goodreadsList
    }

    fun importRecord(record: GoodreadsImporterRequest) {
        val book = retrieveBook(record)

        if (book != null) {
            readingAdapter.createIfNotExists(record.toReadingRequest(book.id))
        }
    }

    private fun retrieveBook(record: GoodreadsImporterRequest): BookResponse? {
        val request = record.toBookRequest()
        bookPort.findByGoodreadsId(request.goodreadsId)?.let {
            return it
        }
        if (!request.isbn.isNullOrBlank()) {
            bookPort.findByIsbn(request.isbn)?.let {
                return it
            }
            val author = authorPort.createIfNotExists(record.author)
            return bookPort.save(request.copy(authorId = author.id))
        }
        logger.error("BOOK IMPORTER - Can't not verify the book $request")
        return null
    }
}