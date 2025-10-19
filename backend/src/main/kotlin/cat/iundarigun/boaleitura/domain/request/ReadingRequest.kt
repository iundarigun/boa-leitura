package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import java.time.LocalDate

data class ReadingRequest(
    val bookId: Long,
    val myRating: Int? = null,
    val dateRead: LocalDate,
    val format: FormatEnum? = null,
    val platform: PlatformEnum? = null,
    val language: String? = null
)
