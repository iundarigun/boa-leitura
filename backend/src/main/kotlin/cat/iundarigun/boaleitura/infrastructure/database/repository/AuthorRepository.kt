package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<AuthorEntity, Long> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
    fun findByNameIsContainingIgnoreCase(name: String, pageable: Pageable): Page<AuthorEntity>
}