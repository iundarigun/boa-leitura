package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.entity.ReadingEntity
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.extensions.toBookSummaryResponse
import cat.iundarigun.boaleitura.extensions.toPageResponse
import cat.iundarigun.boaleitura.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) : BookPort {

    @Transactional(readOnly = true)
    override fun find(title: String?, read: Boolean?, pageRequest: PageRequest): PageResponse<BookSummaryResponse> {
        val specification = Specification.allOf(Specification<BookEntity> { root, query, criteriaBuilder ->
            if (title.isNullOrBlank().not()) {
                criteriaBuilder.and(
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%${title?.uppercase()}%"))
            } else {
                criteriaBuilder.and()
            }
        },
        Specification<BookEntity> { root, query, criteriaBuilder ->
              read?.let {
                  // TODO filter by user id
                  val join: Join<BookEntity,ReadingEntity> = root.join("readings", JoinType.LEFT)
                  if (read) {
                  criteriaBuilder.and(criteriaBuilder.isNotNull(join.get<Long>("id")))
                  } else {
                      criteriaBuilder.and(criteriaBuilder.isNull(join.get<Long>("id")))
                  }
              } ?: criteriaBuilder.and()
            })
        return bookRepository.findAll(specification, pageRequest.toPageable())
            .map { it.toBookSummaryResponse() }
            .toPageResponse()
    }

    @Transactional
    fun createIfNotExists(bookRequest: BookGoodreadsImporterRequest, author: AuthorEntity): BookEntity =
        bookRepository.findByGoodreadsId(bookRequest.goodreadsId).orElseGet {
            bookRepository.save(bookRequest.toBook(author))
        }
}

private fun BookGoodreadsImporterRequest.toBook(author: AuthorEntity): BookEntity =
    BookEntity(
        goodreadsId = this.goodreadsId,
        title = this.title,
        publisherYear = this.publisherYear,
        numberOfPages = this.numberOfPages,
        isbn = this.isbn,
        originalLanguage = this.originalLanguage,
        author = author
    )
