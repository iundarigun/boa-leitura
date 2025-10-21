package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import java.time.LocalDate

data class SearchReadingRequest(
    val keyword: String? = null,
    @field:PastOrPresent
    val dateFrom: LocalDate? = null,
    @field:PastOrPresent
    val dateTo: LocalDate? = null,
    @field:Positive
    val page: Int = 1,
    @field:Min(1)
    @field:Max(250)
    val size: Int = 50,
    val order: ReadingField = ReadingField.DATE_READ,
    val directionAsc: Boolean = false,

    ) {
    enum class ReadingField(val fieldName: String) {
        ID("id"),
        MY_RATING("myRating"),
        TITLE("book.title"),
        AUTHOR("book.author.name"),
        SAGA("book.saga.name"),
        GENRE("book.genre.name"),
        DATE_READ("dateRead")
    }

    fun toPageRequest(): PageRequest =
        PageRequest(page, size, order.fieldName, directionAsc)
}
