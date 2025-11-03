package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadReorderRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.exception.BookNotFoundException
import cat.iundarigun.boaleitura.exception.ToBeReadNotFoundException
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
import kotlin.math.max
import kotlin.math.min

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

    @Transactional
    override fun reorder(id: Long, request: ToBeReadReorderRequest) {
        val movedTbr = toBeReadRepository.findById(id)
            .orElseThrow { throw ToBeReadNotFoundException(id) }
        val targetTbr = toBeReadRepository.findById(request.targetId)
            .orElseThrow { throw ToBeReadNotFoundException(request.targetId) }

        val list = toBeReadRepository.findByBetweenPositions(
            min(movedTbr.position, targetTbr.position), max(movedTbr.position, targetTbr.position)
        )
        val originalPositions = list.map { it.position }
        when (request.direction) {
            ToBeReadReorderRequest.ReorderDirectionEnum.UP -> list.shiftUp()
            ToBeReadReorderRequest.ReorderDirectionEnum.DOWN -> list.shiftDown()
        }.forEachIndexed { i, tbr -> tbr.position = originalPositions[i] }

        toBeReadRepository.saveAll(list)
    }

    @Transactional
    override fun update(id: Long, request: Map<String, Any>) {
        val tbr = toBeReadRepository.findById(id)
            .orElseThrow { throw ToBeReadNotFoundException(id) }
        tbr.bought = request.getOrDefault("bought", tbr.bought) as Boolean
        tbr.done = request.getOrDefault("done", tbr.done) as Boolean

        toBeReadRepository.save(tbr)
    }

    private fun List<ToBeReadEntity>.shiftUp(): List<ToBeReadEntity> =
        listOf(last()) + dropLast(1)

    private fun List<ToBeReadEntity>.shiftDown(): List<ToBeReadEntity> =
        drop(1) + first()
}