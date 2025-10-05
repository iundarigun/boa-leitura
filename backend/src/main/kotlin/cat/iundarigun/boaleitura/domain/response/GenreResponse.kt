package cat.iundarigun.boaleitura.domain.response

data class GenreResponse(
    val id: Long,
    val name: String,
    val parent: GenreResponse?
)