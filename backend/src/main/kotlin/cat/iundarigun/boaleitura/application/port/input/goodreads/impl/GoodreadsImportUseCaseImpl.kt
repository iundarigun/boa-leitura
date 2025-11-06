package cat.iundarigun.boaleitura.application.port.input.goodreads.impl

import cat.iundarigun.boaleitura.application.port.input.book.CreateBookFromGoodreadsUseCase
import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import cat.iundarigun.boaleitura.application.port.input.reading.CreateReadingUseCase
import cat.iundarigun.boaleitura.application.port.input.tbr.AddToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.extensions.toBeReadRequest
import cat.iundarigun.boaleitura.domain.extensions.toReadingRequest
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsRequest
import cat.iundarigun.boaleitura.domain.request.toGoodreadImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.security.UserToken
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.BoaLeituraBusinessException
import cat.iundarigun.boaleitura.exception.UserNotFoundException
import com.opencsv.CSVReader
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import java.io.InputStreamReader

@Component
class GoodreadsImportUseCaseImpl(
    private val jobScheduler: JobScheduler,
    private val userPort: UserPort,
    private val createReadingUseCase: CreateReadingUseCase,
    private val addToBeReadUseCase: AddToBeReadUseCase,
    private val createBookFromGoodreadsUseCase: CreateBookFromGoodreadsUseCase
) : GoodreadsImportUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute(request: GoodreadsRequest): List<GoodreadsImporterRequest> {
        val records = CSVReader(InputStreamReader(request.file.inputStream)).readAll()
        logger.info("Found ${records.size} records")

        val goodreadsList = records.drop(1).map { it ->
            it.toGoodreadImporterRequest()
        }.filter {
            (it.dateRead != null && request.read) ||
                    (it.dateRead == null && request.tbr)
        }

        val user = loggedUser ?: throw UserNotFoundException()
        val userPreferences = userPort.getUserPreferences(user.userId)
        goodreadsList.forEach {
            jobScheduler.enqueue {
                importRecord(it, user.token, userPreferences)
            }
        }

        return goodreadsList
    }

    fun importRecord(record: GoodreadsImporterRequest, token: Jwt, userPreferences: UserPreferences) {
        SecurityContextHolder.getContext().authentication = UserToken(token)
        val book = retrieveBook(record, userPreferences)

        if (book != null) {
            if (record.dateRead != null) {
                runCatching {
                    createReadingUseCase.execute(record.toReadingRequest(book.id, userPreferences))
                }.onFailure {
                    logger.warn("Can not add to the reading for book=${book.title}, reason=${it.message}")
                }
            } else {
                runCatching {
                    addToBeReadUseCase.execute(record.toBeReadRequest(book.id))
                }.onFailure {
                    logger.warn("Can not add to the TBR the book=${book.title}, reason=${it.message}")
                }
            }
        }
    }

    private fun retrieveBook(record: GoodreadsImporterRequest, userPreferences: UserPreferences): BookResponse? {
        return runCatching {
            createBookFromGoodreadsUseCase.execute(record, userPreferences)
        }.onFailure {
            if (it !is BoaLeituraBusinessException) {
                throw it
            }
            logger.warn("Cannot create book: ${it.message}")
        }.getOrNull()
    }
}