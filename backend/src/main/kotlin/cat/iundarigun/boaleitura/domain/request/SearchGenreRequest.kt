package cat.iundarigun.boaleitura.domain.request

data class SearchGenreRequest(
    val name: String? = null,
    val page: Int = 1,
    val size: Int = 50
) {
    fun toPageRequest(): PageRequest =
        PageRequest(page, size, "name")
}
