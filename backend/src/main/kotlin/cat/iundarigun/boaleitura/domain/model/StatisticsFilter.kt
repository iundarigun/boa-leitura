package cat.iundarigun.boaleitura.domain.model

import java.time.LocalDate

data class StatisticsFilter(
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val excludeRereading: Boolean,
)
