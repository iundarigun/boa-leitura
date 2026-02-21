package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.model.StatisticFilter
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDate
import java.time.Month

data class StatisticRequest(
    @field:Min(2000)
    @field:Max(2100)
    val year: Int,
    val excludeRereading: Boolean = false,
) {
    fun toStatisticsFilter(): StatisticFilter {
        val dateFrom = LocalDate.of(year, Month.JANUARY, 1)
        val dateTo = dateFrom.plusYears(1).minusDays(1)
        return StatisticFilter(dateFrom = dateFrom, dateTo =dateTo, excludeRereading = excludeRereading)
    }
}
