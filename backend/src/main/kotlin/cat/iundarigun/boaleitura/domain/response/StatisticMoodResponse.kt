package cat.iundarigun.boaleitura.domain.response

data class StatisticMoodResponse(
    val totalByPageNumber: Map<String, Int>,
    val totalByFormat: Map<String, Int>,
    val totalByOrigin: Map<String, Int>
)
