package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.exception.BookNotFoundException
import cat.iundarigun.boaleitura.exception.GenreNotFoundException
import cat.iundarigun.boaleitura.exception.ReadingNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.merge
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toReading
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toSummaryResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import cat.iundarigun.boaleitura.infrastructure.database.utils.specGreaterOrEqualsThan
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLessOrEqualsThan
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLikeWithOrFields
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class ReadingAdapter(
    private val bookRepository: BookRepository,
    private val readingRepository: ReadingRepository
) : ReadingPort {

    @Transactional(readOnly = true)
    override fun existsByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Boolean =
        readingRepository.existsByBookIdAndDateRead(bookId, dateRead)

    @Transactional(readOnly = true)
    override fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): ReadingResponse? =
        readingRepository.findByBookIdAndDateRead(bookId, dateRead)
            .map { it.toResponse() }
            .getOrNull()

    @Transactional(readOnly = true)
    override fun find(
        keyword: String?,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        pageRequest: PageRequest
    ): PageResponse<ReadingSummaryResponse> {
        val specifications = Specification.allOf<ReadingEntity>(
            specLikeWithOrFields(
                keyword, "book.title",
                "book.originalTitle",
                "book.author.name",
                "book.saga.name"
            ),
            specGreaterOrEqualsThan("dateRead", dateFrom),
            specLessOrEqualsThan("dateRead", dateTo),
        )

        return readingRepository.findAll(specifications, pageRequest.toPageable())
            .map { it.toSummaryResponse() }
            .toPageResponse()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): ReadingResponse {
        val reading = readingRepository.findById(id).orElseThrow { GenreNotFoundException(id) }
        return reading.toResponse()
    }

    @Transactional
    override fun save(request: ReadingRequest, id: Long?): ReadingResponse {
        val book = bookRepository.findById(request.bookId)
            .orElseThrow { throw BookNotFoundException(request.bookId) }
        if (id == null) {
            return readingRepository.save(request.toReading(book)).toResponse()
        }
        val reading = readingRepository.findById(id)
            .orElseThrow { throw ReadingNotFoundException(id) }

        return readingRepository.save(reading.merge(request)).toResponse()
    }

    override fun delete(id: Long) {
        if (!readingRepository.existsById(id)) {
            throw ReadingNotFoundException(id)
        }
        readingRepository.deleteById(id)
    }

    @Transactional
    override fun createIfNotExists(request: ReadingRequest) {
        readingRepository.findByBookIdAndDateRead(request.bookId, request.dateRead).orElseGet {
            val book = bookRepository.findById(request.bookId)
                .orElseThrow { AuthorNotFoundException(request.bookId) }
            readingRepository.save(request.toReading(book))
        }
    }
}