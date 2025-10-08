package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.BookEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookRepository : CrudRepository<BookEntity, Long> {
    fun findByGoodreadsId(bookId: Long): Optional<BookEntity>
    fun countByAuthorId(authorId: Long): Int
    fun countByGenreId(id: Long): Int
    fun countBySagaId(id: Long): Int
}