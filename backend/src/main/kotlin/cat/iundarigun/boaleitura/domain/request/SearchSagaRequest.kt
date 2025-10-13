package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class SearchSagaRequest(
    val name: String? = null,
    @field:Positive
    val page: Int = 1,
    @field:Min(1)
    @field:Max(250)
    val size: Int = 50,
    val directionAsc: Boolean = true,
) {
    fun toPageRequest(): PageRequest =
        PageRequest(page, size, "name", directionAsc)
}
