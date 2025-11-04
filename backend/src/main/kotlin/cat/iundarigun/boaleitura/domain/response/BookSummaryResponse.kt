package cat.iundarigun.boaleitura.domain.response

import java.time.LocalDateTime

data class BookSummaryResponse(
    val id: Long,
    val title: String,
    val author: String,
    val genre: String? = null,
    val saga: SagaResponse? = null,
    val urlImage: String? = null,
    val urlImageSmall: String? = null,
    val createdAt: LocalDateTime,
    val inTbr: Boolean = false,
    val read: Boolean = false,
)
