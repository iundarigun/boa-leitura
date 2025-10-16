package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
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
}