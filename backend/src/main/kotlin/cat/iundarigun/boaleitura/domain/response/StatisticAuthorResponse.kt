package cat.iundarigun.boaleitura.domain.response

data class StatisticAuthorResponse(
    val authorPerGender: Map<String, Int>,
    val totalDistinctAuthors: Int,
    val topAuthors: Map<String, Int>,
    val newAuthors: Map<String, Int>
)
