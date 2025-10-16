package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.SagaEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface SagaRepository : JpaRepository<SagaEntity, Long>, JpaSpecificationExecutor<SagaEntity> {
    fun findByName(name: String): SagaEntity?
    fun existsByName(name: String): Boolean
    @EntityGraph(attributePaths = ["books"])
    fun findByIdOrderByBooksSagaOrder(id: Long): SagaEntity?
}