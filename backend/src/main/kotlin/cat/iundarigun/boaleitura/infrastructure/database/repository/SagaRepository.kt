package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.SagaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface SagaRepository : JpaRepository<SagaEntity, Long> {
    fun findByName(name: String): SagaEntity?
    fun existsByName(name: String): Boolean

    @EntityGraph(attributePaths = ["books"])
    fun findByIdOrderByBooksSagaOrder(id: Long): SagaEntity?

    fun findAllByNameIsContainingIgnoreCase(name: String, pageable: Pageable): Page<SagaEntity>
    fun findAllBy(name: String?, pageable: Pageable): Page<SagaEntity> {
        return if (name.isNullOrBlank()) {
            findAll(pageable)
        } else {
            findAllByNameIsContainingIgnoreCase(name, pageable)
        }
    }
}