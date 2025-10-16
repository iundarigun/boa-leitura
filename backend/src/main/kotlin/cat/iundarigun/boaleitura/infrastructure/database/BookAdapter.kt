package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
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
import cat.iundarigun.boaleitura.infrastructure.database.extensions.merge
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toBookEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toSummaryResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import cat.iundarigun.boaleitura.infrastructure.database.utils.specExistsOrNot
import cat.iundarigun.boaleitura.infrastructure.database.utils.specIsNull
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLikeWithOrFields
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookAdapter(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val sagaRepository: SagaRepository,
    private val genreRepository: GenreRepository,
    private val readingRepository: ReadingRepository
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

    @Transactional(readOnly = true)
    override fun readingCount(id: Long): Int =
        readingRepository.countByBookId(id)

    @Transactional
    override fun delete(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw BookNotFoundException(id)
        }
        bookRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    override fun findMissingImages(): List<BookResponse> {
        val specification = Specification.allOf<BookEntity>(
            specIsNull("urlImage")
        )
        return bookRepository.findAll(specification).map { it.toResponse() }
    }

    @Transactional
    override fun updateUrlImages(id: Long, urlImage: String, urlImageSmall: String?) {
        val book = bookRepository.findById(id)
            .orElseThrow { BookNotFoundException(id) }
        book.urlImage = urlImage
        book.urlImageSmall = urlImageSmall
        bookRepository.save(book)
    }

    @Transactional
    fun createIfNotExists(bookRequest: BookGoodreadsImporterRequest, author: AuthorEntity): BookEntity =
        bookRepository.findByGoodreadsId(bookRequest.goodreadsId).orElseGet {
            bookRepository.save(bookRequest.toBookEntity(author))
        }
}