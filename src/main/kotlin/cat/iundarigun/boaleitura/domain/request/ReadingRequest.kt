package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.FormatEnum
import java.time.LocalDate

data class ReadingRequest(
    val myRating: Int? = null,
    val dateRead: LocalDate,
    val format: FormatEnum? = null,
    val language: String? = null
)
