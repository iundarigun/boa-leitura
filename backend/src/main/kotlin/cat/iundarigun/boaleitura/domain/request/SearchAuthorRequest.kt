package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class SearchAuthorRequest(
    val name: String? = null,
    @field:Positive
    val page: Int = 1,
    @field:Min(1)
    @field:Max(250)
    val size: Int = 50,
    val order: OrderAuthorField = OrderAuthorField.NAME
) {
    enum class OrderAuthorField(val fieldName: String) {
        ID("id"),
        NAME("name"),
        NATIONALITY("nationality"),
        GENDER("gender")
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName)
}
