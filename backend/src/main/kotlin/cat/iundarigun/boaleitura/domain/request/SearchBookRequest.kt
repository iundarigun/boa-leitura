package cat.iundarigun.boaleitura.domain.request

data class SearchBookRequest(
    val title: String? = null,
    val read: Boolean? = null,
    val page: Int = 1,
    val size: Int = 50,
    val order: OrderAuthorField = OrderAuthorField.TITLE
) {
    enum class OrderAuthorField(val fieldName: String) {
        ID("id"),
        TITLE("title"),
        AUTHOR("author.name"),
        SAGA("saga.name"),
        GENDER("gender.name"),
        CREATED_AT("created_at")
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName)
}
