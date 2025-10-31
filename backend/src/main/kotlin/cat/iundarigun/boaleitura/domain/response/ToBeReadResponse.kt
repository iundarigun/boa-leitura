package cat.iundarigun.boaleitura.domain.response

import java.time.LocalDate

data class ToBeReadResponse(
    val id: Long,
    val position: Long,
    val book: BookSummaryResponse,
    val bought: Boolean = false,
    val done: Boolean,
    val platforms: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val notes: String? = null,
    val addedAt: LocalDate
)