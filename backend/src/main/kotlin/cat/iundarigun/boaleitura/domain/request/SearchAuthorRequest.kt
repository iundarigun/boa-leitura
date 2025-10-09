package cat.iundarigun.boaleitura.domain.request

data class SearchAuthorRequest(
    val name: String? = null,
    val page: Int = 1,
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
