package cat.iundarigun.boaleitura.domain.response

data class PageResponse<T>(
    val content: List<T>,
    val page: Int,
    val totalPages: Int
)
