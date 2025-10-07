package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<AuthorEntity, Long> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
}