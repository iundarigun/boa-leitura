package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.SagaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.lang.Nullable

interface SagaRepository : JpaRepository<SagaEntity, Long>, JpaSpecificationExecutor<SagaEntity> {
    @EntityGraph(attributePaths = ["statuses"])
    fun findByName(name: String): SagaEntity?
    fun existsByName(name: String): Boolean
    @EntityGraph(attributePaths = ["books"])
    fun findByIdOrderByBooksSagaOrder(id: Long): SagaEntity?
    @EntityGraph(attributePaths = ["statuses"])
    override fun findAll(@Nullable specification: Specification<SagaEntity>?, pageable: Pageable): Page<SagaEntity>
}