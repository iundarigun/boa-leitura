package cat.iundarigun.boaleitura.domain.model

data class StatisticAuthor(
    val authorGender: Map<String, Int>,
    val authorCounts: List<StatisticAuthorCount>
)

class StatisticAuthorCount(
    val name: String,
    val newAuthor: Boolean,
    val count: Int
)
