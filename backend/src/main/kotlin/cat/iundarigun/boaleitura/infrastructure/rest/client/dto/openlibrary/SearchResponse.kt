package cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class SearchResponse(
    val numFound: Int = 0,
    val start: Int = 0,
    val numFoundExact: Boolean = false,
    val docs: List<DocumentResponse> = emptyList(),
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DocumentResponse(
    val authorKey: List<String> = emptyList(),
    val authorName: List<String> = emptyList(),
    val coverEditionKey: String? = null,
    val title: String
)