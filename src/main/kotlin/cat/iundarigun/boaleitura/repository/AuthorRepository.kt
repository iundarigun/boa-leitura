package cat.iundarigun.boaleitura.repository

import cat.iundarigun.boaleitura.domain.entity.Author
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface AuthorRepository: CrudRepository<Author, Long> {
    fun findByName(name: String): Optional<Author>
}