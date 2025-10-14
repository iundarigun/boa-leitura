package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel

data class BookRequest(
    val title: String,
    val authorId: Long,
    val genreId: Long,
    val language: String,
    val numberOfPages: Int,
    val publisherYear: Int,
    val originalEdition: BookOriginalEditionModel? = null,
    val saga: BookSagaRequest? = null,
    val isbn: String,
    val urlImage: String? = null,
    val urlImageSmall: String? = null,
)

data class BookSagaRequest(
    val id: Long,
    val order: Double,
    val mainTitle: Boolean
)