package cat.iundarigun.boaleitura.domain.request

data class PageRequest(
    val page: Int = 1,
    val size: Int = 50,
    val order: String = "id",
    val directionAsc: Boolean = true
)
