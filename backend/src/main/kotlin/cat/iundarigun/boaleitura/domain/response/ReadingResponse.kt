package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import java.time.LocalDate

data class ReadingResponse(
    val id: Long,
    val book: BookResponse,
    val myRating: Double? = null,
    val language: String? = null,
    val dateRead: LocalDate,
    val format: FormatEnum?,
    val platform: PlatformEnum?,
    val positionInYear: Int,
)