package cat.iundarigun.boaleitura.domain.request

data class BookRequest(
    val goodreadsId: Long,
    val title: String,
    val numberOfPages: Int? = null,
    val publisherYear: Int,
    val isbn: String? = null,
    val isbn13: String? = null,
    val originalLanguage: String? = null
)