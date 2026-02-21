package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class StatisticsRequest(
    @field:Min(2000)
    @field:Max(2100)
    val year: Int
)
