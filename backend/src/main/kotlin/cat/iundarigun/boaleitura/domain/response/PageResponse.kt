package cat.iundarigun.boaleitura.domain.response

data class PageResponse<T>(
    val content: List<T> = emptyList(),
    val page: Int = 1,
    val totalPages: Int = 1
)
