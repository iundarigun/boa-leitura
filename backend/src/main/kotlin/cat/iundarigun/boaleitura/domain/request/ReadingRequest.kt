package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.enums.TagEnum
import java.time.LocalDate

data class ReadingRequest(
    val bookId: Long,
    val myRating: Int? = null,
    val dateRead: LocalDate,
    val format: TagEnum? = null,
    val language: String? = null
)
