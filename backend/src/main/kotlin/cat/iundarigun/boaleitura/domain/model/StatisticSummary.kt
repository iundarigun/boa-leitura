package cat.iundarigun.boaleitura.domain.model

data class StatisticSummary(
    val amountOfTotalReading: Int,
    val amountOfRereading: Int,
    val totalPages: Int,
    val averagePages: Double,
    val averageRating: Double,
    val bestRating: Double,
    val worseRange: Double
)
