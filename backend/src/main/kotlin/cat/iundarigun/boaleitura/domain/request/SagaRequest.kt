package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class SagaRequest(
    @field:Size(min = 3, max = 100)
    val name: String,
    @field:PositiveOrZero
    val totalMainTitles: Int = 0,
    @field:PositiveOrZero
    val totalComplementaryTitles: Int = 0,
    val concluded: Boolean = false
)
