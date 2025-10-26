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
    val order: SagaField = SagaField.NAME,
    val directionAsc: Boolean = true,
) {
    enum class SagaField(val fieldName: String) {
        NAME("name"),
        STATUS("statuses.status"),
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName, directionAsc)
}
