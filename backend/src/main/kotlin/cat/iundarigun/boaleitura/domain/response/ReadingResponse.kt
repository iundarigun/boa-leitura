package cat.iundarigun.boaleitura.domain.response

import java.time.LocalDate

data class ReadingResponse(
    val id: Long,
    val book: BookSummaryResponse,
    val myRating: Double? = null,
    val language: String? = null,
    val dateRead: LocalDate
)