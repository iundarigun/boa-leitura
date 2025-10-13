package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel

data class BookResponse(
    val id: Long,
    val title: String,
    val author: AuthorResponse,
    val genre: GenreResponse? = null,
    val language: String?,
    val numberOfPages: Int,
    val publisherYear: Int? = null,
    val originalEdition: BookOriginalEditionModel? = null,
    val saga: SagaBookResponse? = null,
    val isbn: String? = null,
    val urlImage: String? = null,
    val urlImageSmall: String? = null,
    val read: Boolean
)

data class SagaBookResponse(
    val saga: SagaResponse,
    val order: Double,
    val mainTitle: Boolean,
)