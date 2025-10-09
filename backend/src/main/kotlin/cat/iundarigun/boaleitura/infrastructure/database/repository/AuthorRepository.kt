package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : JpaRepository<AuthorEntity, Long> {
    fun findByName(name: String): AuthorEntity?
    fun existsByName(name: String): Boolean
//    fun findAll(sort: Sort): List<AuthorEntity>
}