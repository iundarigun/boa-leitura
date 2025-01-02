package cat.iundarigun.boaleitura.service

import cat.iundarigun.boaleitura.domain.entity.Author
import cat.iundarigun.boaleitura.domain.entity.Book
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) {

    @Transactional
    fun createIfNotExists(bookRequest: BookRequest, author: Author): Book =
        bookRepository.findByGoodreadsId(bookRequest.goodreadsId).orElseGet {
            bookRepository.save(bookRequest.toBook(author))
        }
}

private fun BookRequest.toBook(author: Author): Book =
    Book(
        goodreadsId = this.goodreadsId,
        title = this.title,
        publisherYear = this.publisherYear,
        numberOfPages = this.numberOfPages,
        isbn = this.isbn,
        isbn13 = this.isbn13,
        originalLanguage = this.originalLanguage,
        author = author
    )
