package cat.iundarigun.boaleitura.repository

import cat.iundarigun.boaleitura.domain.entity.Book
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookRepository: CrudRepository<Book, Long> {
    fun findByGoodreadsId(bookId: Long): Optional<Book>
    fun countByAuthorId(authorId: Long): Int
}