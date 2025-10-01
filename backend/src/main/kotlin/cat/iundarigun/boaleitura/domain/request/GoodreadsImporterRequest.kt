package cat.iundarigun.boaleitura.domain.request

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class GoodreadsImporterRequest(
    val bookId: Long,
    val title: String,
    val author: String,
    val authorLF: String,
    val AdditionalAuthors: String,
    val isbn: String,
    val isbn13: String,
    val myRating: Int? = null,
    val averageRating: Double? = null,
    val publisher: String,
    val binding: String,
    val numberOfPages: Int? = null,
    val yearPublished: Int,
    val originalPublicationYear: Int? = null,
    val dateRead: LocalDate? = null,
    val dateAdded: LocalDate? = null,
    val bookshelves: List<String>,
    val bookshelvesWithPositions: String? = null,
    val exclusiveShelf: String? = null,
    val myReview: String? = null,
    val spoiler: String? = null,
    val privateNotes: String? = null,
    val readCount: String? = null,
    val ownedCopies: String? = null
)

fun Array<String>.toGoodreadImporterRequest(): GoodreadsImporterRequest =
    GoodreadsImporterRequest(
        bookId = this[0].toLong(),
        title = this[1],
        author = this[2],
        authorLF = this[3],
        AdditionalAuthors = this[4],
        isbn = this[5],
        isbn13 = this[6],
        myRating = this[7].toIntOrNull(),
        averageRating = this[8].toDoubleOrNull(),
        publisher = this[9],
        binding = this[10],
        numberOfPages = this[11].toIntOrNull(),
        yearPublished = this[12].toInt(),
        originalPublicationYear = this[13].toIntOrNull(),
        dateRead = this[14].let { if (it.isBlank()) null else LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy/MM/dd")) },
        dateAdded = this[15].let { if (it.isBlank()) null else LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy/MM/dd")) },
        bookshelves = this[16].split(",").map { it.trim() },
        bookshelvesWithPositions = this[17],
        exclusiveShelf = this[18],
        myReview = this[19],
        spoiler = this[20],
        privateNotes = this[21],
        readCount = this[22],
        ownedCopies = this[23]
    )

