package cat.iundarigun.boaleitura.domain.response

import java.time.LocalDateTime

data class BookSummaryResponse(
    val id: Long,
    val title: String,
    val author: String,
    val genre: String? = null,
    val saga: String? = null,
    val urlImageSmall: String? = null,
    val createdAt: LocalDateTime,
    val read: Boolean = false,
)
