package cat.iundarigun.boaleitura.application.port.input.goodreads.impl

import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.extensions.toBookRequest
import cat.iundarigun.boaleitura.domain.extensions.toReadingRequest
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.toGoodreadImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.security.UserToken
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.UserNotFoundException
import com.opencsv.CSVReader
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.InputStreamReader

@Component
class GoodreadsImportUseCaseImpl(
    private val authorPort: AuthorPort,
    private val bookPort: BookPort,
    private val readingAdapter: ReadingPort,
    private val jobScheduler: JobScheduler,
    private val userPort: UserPort
) : GoodreadsImportUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute(file: InputStream): List<GoodreadsImporterRequest> {
        val records = CSVReader(InputStreamReader(file)).readAll()
        logger.info("Found ${records.size} records")

        val goodreadsList = records.subList(1, records.size).map { it ->
            it.toGoodreadImporterRequest()
        }
        val user = loggedUser ?: throw UserNotFoundException()
        val userPreferences = userPort.getUserPreferences(user.userId)
        goodreadsList.filter { it.dateRead != null }.forEach {
            jobScheduler.enqueue {
                importRecord(it, user, userPreferences)
            }
        }

        return goodreadsList
    }

    fun importRecord(record: GoodreadsImporterRequest, loggedUser: UserToken, userPreferences: UserPreferences) {
        SecurityContextHolder.getContext().authentication = loggedUser
        val book = retrieveBook(record, userPreferences)

        if (book != null) {
            readingAdapter.createIfNotExists(record.toReadingRequest(book.id, userPreferences))
        }
    }

    private fun retrieveBook(record: GoodreadsImporterRequest, userPreferences: UserPreferences): BookResponse? {
        val request = record.toBookRequest(userPreferences)
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