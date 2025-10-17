package cat.iundarigun.boaleitura.domain.response

data class BookInformationResponse(
    val title: String? = null,
    val isbn: String? = null,
    val author: AuthorResponse? = null,
    val numberOfPages: Int? = null,
    val urlImage: String? = null,
    val urlImageSmall: String? = null,
    val publisherYear: Int? = null,
    val language: String? = null,
)
