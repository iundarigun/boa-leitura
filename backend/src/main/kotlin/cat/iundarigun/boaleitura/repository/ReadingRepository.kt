package cat.iundarigun.boaleitura.repository

import cat.iundarigun.boaleitura.domain.entity.Reading
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.Optional

interface ReadingRepository : CrudRepository<Reading, Long> {
    fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Optional<Reading>
}