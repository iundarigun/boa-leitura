package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.exception.BookNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.entity.ToBeReadEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ToBeReadRepository
import cat.iundarigun.boaleitura.infrastructure.database.utils.specIs
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLikeWithOrFields
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToBeReadAdapter(
    private val toBeReadRepository: ToBeReadRepository,
    private val bookRepository: BookRepository,
) : ToBeReadPort {
    @Transactional(readOnly = true)
    override fun find(
        keyword: String?,
        bought: Boolean?,
        done: Boolean?,
        pageRequest: PageRequest
    ): PageResponse<ToBeReadResponse> {
        val specifications = Specification.allOf<ToBeReadEntity>(
            specLikeWithOrFields(
                keyword, "book.title",
                "book.originalTitle",
                "book.author.name",
                "book.saga.name"
            ),
            specIs(bought, "bought"),
            specIs(done, "done")
        )

        return toBeReadRepository.findAll(specifications, pageRequest.toPageable())
            .map { it.toResponse() }
            .toPageResponse()
    }

    @Transactional(readOnly = true)
    override fun existsByBook(bookId: Long): Boolean =
        toBeReadRepository.existsByBookId(bookId)

    @Transactional
    override fun save(request: ToBeReadRequest): ToBeReadResponse {
        val book = bookRepository.findById(request.bookId)
            .orElseThrow { throw BookNotFoundException(request.bookId) }
        val position = request.position ?: ((toBeReadRepository.findMaxPosition() ?: 0) + 1)

        return toBeReadRepository.save(request.toEntity(book, position)).toResponse()
    }
}