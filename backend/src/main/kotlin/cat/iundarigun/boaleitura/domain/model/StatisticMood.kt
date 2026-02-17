package cat.iundarigun.boaleitura.domain.model

data class StatisticMood(
    val totalByPageNumber: Map<String, Int>,
    val formatAndOrigin: List<StatisticFormatAndOrigin>,
    val totalByGenre: Map<String, Int>
)

data class StatisticFormatAndOrigin(
    val format: String,
    val origin: String,
    val count: Int
)
