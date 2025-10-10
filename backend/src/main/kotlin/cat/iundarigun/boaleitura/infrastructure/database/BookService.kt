package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.request.BookGoodreadsImporterRequest
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) {

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
