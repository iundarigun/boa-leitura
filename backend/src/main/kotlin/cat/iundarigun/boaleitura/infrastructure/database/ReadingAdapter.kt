package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toReading
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import cat.iundarigun.boaleitura.infrastructure.database.utils.specGreaterOrEqualsThan
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLessOrEqualsThan
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLikeWithOrFields
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ReadingAdapter(
    private val bookRepository: BookRepository,
    private val readingRepository: ReadingRepository
) : ReadingPort {

    @Transactional
    override fun createIfNotExists(request: ReadingRequest) {
        readingRepository.findByBookIdAndDateRead(request.bookId, request.dateRead).orElseGet {
            val book = bookRepository.findById(request.bookId)
                .orElseThrow { AuthorNotFoundException(request.bookId) }
            readingRepository.save(request.toReading(book))
        }
    }

    override fun find(
        keyword: String?,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        pageRequest: PageRequest
    ): PageResponse<ReadingResponse> {
        val specifications = Specification.allOf<ReadingEntity>(
            specLikeWithOrFields(keyword, "book.title",
                "book.originalTitle",
                "book.author.name",
                "book.saga.name"),
            specGreaterOrEqualsThan("dateRead", dateFrom),
            specLessOrEqualsThan("dateRead", dateTo),
        )

        return readingRepository.findAll(specifications, pageRequest.toPageable())
            .map { it.toResponse() }
            .toPageResponse()
    }
}