package cat.iundarigun.boaleitura.infrastructure.rest.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenLibraryResponse(
    val url: String?,
    val key: String?,
    val title: String?,
    val authors: List<OpenLibraryAuthorResponse> = emptyList(),
    val numberOfPages: Int?,
    val identifiers: OpenLibraryIdentifierResponse?,
    val publishers: List<OpenLibraryPublisherResponse> = emptyList(),
    val publishDate: String?,
    val cover: OpenLibraryCoverResponse?
) {
    fun isMissingInformation(): Boolean =
        authors.isEmpty() ||
                numberOfPages == null ||
                numberOfPages == 0 ||
                publishDate == null ||
                cover == null ||
                cover.small == null ||
                cover.large == null

    fun getIsbn(): String? =
        if (this.identifiers?.isbn13.isNullOrEmpty()) {
            null
        } else {
            this.identifiers!!.isbn13!![0]
        }

    @Suppress("MagicNumber")
    fun getPublisherYear(): Int? =
        this.publishDate?.substring(this.publishDate.length - 4)?.toIntOrNull()
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenLibraryAuthorResponse(
    val name: String?,
    val url: String?
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenLibraryIdentifierResponse(
    @field:JsonProperty("isbn_10")
    val isbn10: List<String>?,
    @field:JsonProperty("isbn_13")
    val isbn13: List<String>?,
    val openlibrary: List<String>?,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenLibraryPublisherResponse(
    val name: String?
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenLibraryCoverResponse(
    val small: String?,
    val medium: String?,
    val large: String?,
)
