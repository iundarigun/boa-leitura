package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.ReadingEntity
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.Optional

interface ReadingRepository : CrudRepository<ReadingEntity, Long> {
    fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Optional<ReadingEntity>
}