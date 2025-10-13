package cat.iundarigun.boaleitura.domain

data class BookInformation(
    val title: String?,
    val author: String?,
    val numberOfPages: Int?,
    val isbn: String?,
    val publisherYear: Int?,
    val urlImage: String? = null,
    val urlImageSmall: String? = null,
)