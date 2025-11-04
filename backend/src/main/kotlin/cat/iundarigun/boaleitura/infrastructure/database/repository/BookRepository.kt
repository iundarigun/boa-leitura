package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.lang.Nullable
import java.util.*

interface BookRepository : JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    fun findByGoodreadsId(bookId: Long): BookEntity?
    fun countByAuthorId(authorId: Long): Int
    fun countByGenreId(id: Long): Int
    fun countBySagaId(id: Long): Int
    @EntityGraph(attributePaths = ["author", "saga", "genre", "readings"])
    override fun findAll(@Nullable specification: Specification<BookEntity>?, pageable: Pageable): Page<BookEntity>
    fun existsByIsbn(isbn: String): Boolean
    fun findByIsbn(isbn: String): BookEntity?
    @Query("""
        SELECT count(b.id) > 0 FROM book b
        LEFT JOIN reading r ON 
            b.id = r.book_id
        LEFT JOIN to_be_read tbr ON
            b.id = tbr.book_id
        WHERE b.id = :id AND
              (tbr.id is not null OR r.id is not null)
        """, nativeQuery = true)
    fun hasReadsOrTbr(id: Long): Boolean
}