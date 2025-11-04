package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class SearchToBeReadRequest(
    val keyword: String? = null,
    val bought: Boolean? = null,
    val done: Boolean? = false,
    @field:Positive
    val page: Int = 1,
    @field:Min(1)
    @field:Max(250)
    val size: Int = 50,
    val order: OrderToBeReadField = OrderToBeReadField.POSITION,
    val directionAsc: Boolean = true,

    ) {
    enum class OrderToBeReadField(val fieldName: String) {
        POSITION("position"),
        TITLE("book.title"),
        CREATED_AT("createdAt")
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName, directionAsc)
}
