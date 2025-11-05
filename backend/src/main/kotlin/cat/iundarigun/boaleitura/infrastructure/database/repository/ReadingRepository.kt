package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.lang.Nullable
import java.time.LocalDate
import java.util.*

interface ReadingRepository : JpaRepository<ReadingEntity, Long>, JpaSpecificationExecutor<ReadingEntity> {
    fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Optional<ReadingEntity>

    @EntityGraph(attributePaths = ["book", "book.author", "book.saga", "book.genre"])
    override fun findAll(
        @Nullable specification: Specification<ReadingEntity>?,
        pageable: Pageable
    ): Page<ReadingEntity>

    fun existsByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Boolean

    @Query("""
        SELECT r.id FROM Reading r 
        WHERE YEAR(r.dateRead) = 
            (SELECT YEAR(r2.dateRead) FROM Reading r2 WHERE r2.id = :id )
        ORDER BY r.dateRead
        """)
    fun readingsInTheYear(id: Long): List<Long>
}