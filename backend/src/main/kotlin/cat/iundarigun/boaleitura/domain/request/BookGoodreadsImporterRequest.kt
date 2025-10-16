package cat.iundarigun.boaleitura.domain.request

data class BookGoodreadsImporterRequest(
    val goodreadsId: Long,
    val title: String,
    val numberOfPages: Int? = null,
    val publisherYear: Int,
    val isbn: String? = null,
    val language: String? = null,
    val authorId: Long? = null,
)