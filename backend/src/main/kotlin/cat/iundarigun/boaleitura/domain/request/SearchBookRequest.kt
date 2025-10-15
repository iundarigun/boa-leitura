package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class SearchBookRequest(
    val title: String? = null,
    val read: Boolean? = null,
    @field:Positive
    val page: Int = 1,
    @field:Min(1)
    @field:Max(250)
    val size: Int = 50,
    val order: OrderBookField = OrderBookField.TITLE,
    val directionAsc: Boolean = true,

    ) {
    enum class OrderBookField(val fieldName: String) {
        ID("id"),
        TITLE("title"),
        AUTHOR("author.name"),
        SAGA("saga.name"),
        GENRE("genre.name"),
        CREATED_AT("createdAt")
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName, directionAsc)
}
