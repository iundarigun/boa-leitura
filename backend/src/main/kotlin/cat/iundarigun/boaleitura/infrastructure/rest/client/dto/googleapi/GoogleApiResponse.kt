package cat.iundarigun.boaleitura.infrastructure.rest.client.dto.googleapi

data class GoogleApiResponse(
    val kind: String?,
    val totalItems: Int?,
    val items: List<GoogleApiItemResponse> = emptyList()
)

data class GoogleApiItemResponse(
    val kind: String?,
    val id: String?,
    val etag: String?,
    val selfLink: String?,
    val volumeInfo: GoogleApiVolumeInfoResponse,
    val safeInfo: GoogleApiSafeInfoResponse?,
    val accessInfo: GoogleApiAccessInfoResponse?,
    val searchInfo: SearchInfoResponse?
) {
    fun getIsbn(): String? =
        this.volumeInfo.industryIdentifiers.find { it.type == "ISBN_13" }?.identifier

    @Suppress("MagicNumber")
    fun getPublisherYear(): Int? =
        this.volumeInfo.publishedDate?.substring(0, 4)?.toIntOrNull()
}

data class GoogleApiVolumeInfoResponse(
    val title: String,
    val subtitle: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val industryIdentifiers: List<GoogleApiIndustryIdentifierResponse> = emptyList(),
    val readingModes: GoogleApiReadingModeResponse?,
    val pageCount: Int?,
    val printType: String?,
    val categories: List<String> = emptyList(),
    val averageRating: Double?,
    val ratingsCount: Int?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean?,
    val contentVersion: String?,
    val panelizationSummary: GoogleApiPanelizationSummaryResponse?,
    val imageLinks: GoogleApiImageLinksResponse?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val cononicalVolumeLink: String?,
) {
    fun getFullTitle(): String =
        this.title + (this.subtitle?.let { " $it" } ?: "")
}

data class GoogleApiIndustryIdentifierResponse(
    val type: String,
    val identifier: String
)

data class GoogleApiReadingModeResponse(
    val text: Boolean?,
    val image: Boolean?
)

data class GoogleApiPanelizationSummaryResponse(
    val containsEpubBubbles: Boolean?,
    val containsImageBubbles: Boolean?,
)

data class GoogleApiImageLinksResponse(
    val smallThumbnail: String?,
    val thumbnail: String?,
)

data class GoogleApiSafeInfoResponse(
    val country: String?,
    val saleability: String?,
    val isEbook: Boolean?
)

data class GoogleApiAccessInfoResponse(
    val country: String?,
    val viewability: String?,
    val embeddable: Boolean?,
    val publicDomain: Boolean?,
    val textToSpeechPermission: String?,
    val epub: GoogleApiAvailableResponse?,
    val pdf: GoogleApiAvailableResponse?,
    val webReaderLink: String?,
    val accessViewStatus: String?,
    val quoteSharingAllowed: Boolean?,
)

data class GoogleApiAvailableResponse(val available: Boolean)

data class SearchInfoResponse(val textSnippet: String?)