package cat.iundarigun.boaleitura.domain.model

import java.time.LocalDate

data class StatisticFilter(
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val excludeRereading: Boolean,
)
