package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.SagaEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

interface SagaRepository : CrudRepository<SagaEntity, Long> {
    fun findByName(name: String): SagaEntity?
    fun existsByName(name: String): Boolean
    @EntityGraph(attributePaths = ["books"])
    fun findByIdOrderByBooksSagaOrder(id: Long): SagaEntity?
}