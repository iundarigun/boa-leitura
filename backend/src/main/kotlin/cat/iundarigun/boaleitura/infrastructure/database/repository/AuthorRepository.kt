package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AuthorRepository : JpaRepository<AuthorEntity, Long>, JpaSpecificationExecutor<AuthorEntity> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
}