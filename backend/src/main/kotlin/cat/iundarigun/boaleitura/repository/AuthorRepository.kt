package cat.iundarigun.boaleitura.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface AuthorRepository : CrudRepository<AuthorEntity, Long> {
    fun findByName(name: String): Optional<AuthorEntity>
}