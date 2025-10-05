package cat.iundarigun.boaleitura.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookRepository : CrudRepository<BookEntity, Long> {
    fun findByGoodreadsId(bookId: Long): Optional<BookEntity>
    fun countByAuthorId(authorId: Long): Int
}