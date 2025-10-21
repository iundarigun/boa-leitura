package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
import java.time.LocalDate

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
    val read: Boolean,
    val readings: List<ReadingBookResponse> = emptyList(),
)

data class SagaBookResponse(
    val saga: SagaResponse,
    val order: Double,
    val mainTitle: Boolean,
)

data class ReadingBookResponse(
    val id: Long,
    val myRating: Double? = null,
    val language: String? = null,
    val dateRead: LocalDate,
    val format: FormatEnum?,
    val platform: PlatformEnum?,
)