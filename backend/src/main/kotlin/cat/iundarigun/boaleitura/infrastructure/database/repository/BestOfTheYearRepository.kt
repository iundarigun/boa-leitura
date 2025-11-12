package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.infrastructure.database.entity.BestOfTheYearEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BestOfTheYearRepository : JpaRepository<BestOfTheYearEntity, Long> {
    fun findByYear(year: Int): BestOfTheYearEntity?
}