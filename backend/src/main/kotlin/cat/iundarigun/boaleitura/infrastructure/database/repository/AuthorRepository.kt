package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface AuthorRepository : CrudRepository<AuthorEntity, Long> {
    fun findByName(name: String): Optional<AuthorEntity>
    fun existsByName(name: String): Boolean
}