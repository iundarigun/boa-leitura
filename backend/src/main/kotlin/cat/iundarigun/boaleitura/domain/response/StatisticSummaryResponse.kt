package cat.iundarigun.boaleitura.domain.response

data class StatisticSummaryResponse(
    val year: Int,
    val amountOfTotalReading: Int,
    val amountOfRereading: Int,
    val totalPages: Int,
    val averagePages: Double,
    val averageRating: Double,
    val bestBooks: StatisticRatingResponse,
    val worseBooks: StatisticRatingResponse,
)

data class StatisticRatingResponse(
    val rating: Double,
    val bookList: List<StatisticBookResponse> = emptyList(),
)

data class StatisticBookResponse(
    val id: Long,
    val title: String,
    val urlImage: String? = null,
)