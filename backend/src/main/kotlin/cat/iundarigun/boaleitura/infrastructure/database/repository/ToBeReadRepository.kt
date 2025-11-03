package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.ToBeReadEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.lang.Nullable

interface ToBeReadRepository : JpaRepository<ToBeReadEntity, Long>, JpaSpecificationExecutor<ToBeReadEntity> {
    @EntityGraph(attributePaths = ["book", "book.author", "book.saga", "book.genre"])
    override fun findAll(
        @Nullable specification: Specification<ToBeReadEntity>?,
        pageable: Pageable
    ): Page<ToBeReadEntity>

    fun existsByBookId(bookId: Long): Boolean

    @Query("SELECT max(position) from ToBeRead")
    fun findMaxPosition(): Long?

    @Query("""
        SELECT tbr from ToBeRead tbr
        WHERE tbr.position >= :min AND 
              tbr.position <= :max AND
              tbr.done is not true
        ORDER BY tbr.position ASC
        """)
    fun findByBetweenPositions(min: Long, max: Long): List<ToBeReadEntity>
}