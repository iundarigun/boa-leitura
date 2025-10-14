package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.exception.BookNotFoundException
import cat.iundarigun.boaleitura.exception.GenreNotFoundException
import cat.iundarigun.boaleitura.exception.SagaNotFoundException
import cat.iundarigun.boaleitura.extensions.merge
import cat.iundarigun.boaleitura.extensions.toBookEntity
import cat.iundarigun.boaleitura.extensions.toEntity
import cat.iundarigun.boaleitura.extensions.toPageResponse
import cat.iundarigun.boaleitura.extensions.toPageable
import cat.iundarigun.boaleitura.extensions.toResponse
import cat.iundarigun.boaleitura.extensions.toSummaryResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookAdapter(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val sagaRepository: SagaRepository,
    private val genreRepository: GenreRepository
) : BookPort {

    @Transactional(readOnly = true)
    override fun find(title: String?, read: Boolean?, pageRequest: PageRequest): PageResponse<BookSummaryResponse> {
        val specification = Specification.allOf<BookEntity>(
            specLikeWithOrFields(title, "title", "originalTitle"),
            specExistsOrNot(read, "readings")
        )

        return bookRepository.findAll(specification, pageRequest.toPageable())
            .map { it.toSummaryResponse() }
            .toPageResponse()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): BookResponse {
        val book = bookRepository.findById(id)
            .orElseThrow { BookNotFoundException(id) }
        return book.toResponse()
    }

    @Transactional(readOnly = true)
    override fun existsByIsbn(isbn: String): Boolean =
        bookRepository.existsByIsbn(isbn)

    @Transactional(readOnly = true)
    override fun findByIsbn(isbn: String): BookResponse? =
        bookRepository.findByIsbn(isbn)?.toResponse()

    @Transactional
    override fun save(request: BookRequest, id: Long?): BookResponse {
        val author = authorRepository.findById(request.authorId)
            .orElseThrow { throw AuthorNotFoundException(request.authorId) }
        val genre = genreRepository.findById(request.genreId)
            .orElseThrow { throw GenreNotFoundException(request.genreId) }
        val saga = request.saga?.let { sagaRequest ->
            sagaRepository.findById(sagaRequest.id)
                .orElseThrow { throw SagaNotFoundException(sagaRequest.id) }
        }
        if (id == null) {
            return bookRepository.save(request.toEntity(author, genre, saga)).toResponse()
        }
        val book = bookRepository.findById(id)
            .orElseThrow { BookNotFoundException(id) }
        return bookRepository.save(book.merge(request, author, genre, saga)).toResponse()
    }

    @Transactional
    fun createIfNotExists(bookRequest: BookGoodreadsImporterRequest, author: AuthorEntity): BookEntity =
        bookRepository.findByGoodreadsId(bookRequest.goodreadsId).orElseGet {
            bookRepository.save(bookRequest.toBookEntity(author))
        }
}