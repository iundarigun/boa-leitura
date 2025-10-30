package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): UserEntity?
}