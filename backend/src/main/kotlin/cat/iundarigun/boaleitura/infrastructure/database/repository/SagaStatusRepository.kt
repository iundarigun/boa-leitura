package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.SagaStatusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SagaStatusRepository : JpaRepository<SagaStatusEntity, Long> {
    fun findBySagaId(id: Long): SagaStatusEntity?
}