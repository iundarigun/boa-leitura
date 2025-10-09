package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<AuthorEntity, Long> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
    fun findAllByNameIsContainingIgnoreCase(name: String, pageable: Pageable): Page<AuthorEntity>

    fun findAllBy(name: String?, pageable: Pageable): Page<AuthorEntity> {
        return if (name.isNullOrBlank()) {
            findAll(pageable)
        } else {
            findAllByNameIsContainingIgnoreCase(name, pageable)
        }
    }
}