package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<AuthorEntity, Long> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
//    fun findAll(sort: Sort): List<AuthorEntity>
}