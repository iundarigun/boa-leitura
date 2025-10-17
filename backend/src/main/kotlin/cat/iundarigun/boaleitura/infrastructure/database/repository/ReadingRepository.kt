package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.time.LocalDate
import java.util.*

interface ReadingRepository : JpaRepository<ReadingEntity, Long>, JpaSpecificationExecutor<ReadingEntity> {
    fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Optional<ReadingEntity>
    fun countByBookId(id: Long): Int
}