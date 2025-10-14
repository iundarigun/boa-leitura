package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class BookRequest(
    @field:Size(min = 1, max = 255)
    val title: String,
    val authorId: Long,
    val genreId: Long,
    @field:Size(min = 1, max = 100)
    val language: String,
    @field:PositiveOrZero
    val numberOfPages: Int,
    @Suppress("MagicNumber")
    @field:Min(0)
    @field:Max(2100)
    val publisherYear: Int,
    @field:Valid
    val originalEdition: BookOriginalEditionModel? = null,
    val saga: BookSagaRequest? = null,
    @field:Size(min = 10, max = 20)
    val isbn: String,
    @field:URL
    @field:Size(max = 512)
    val urlImage: String? = null,
    @field:URL
    @field:Size(max = 512)
    val urlImageSmall: String? = null,
)

data class BookSagaRequest(
    val id: Long,
    val order: Double,
    val mainTitle: Boolean
)