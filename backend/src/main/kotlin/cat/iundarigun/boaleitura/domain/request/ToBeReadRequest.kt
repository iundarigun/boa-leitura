package cat.iundarigun.boaleitura.domain.request

data class ToBeReadRequest(
    val bookId: Long,
    val position: Long? = null,
    val bought: Boolean = false,
    val platforms: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val notes: String? = null
)
