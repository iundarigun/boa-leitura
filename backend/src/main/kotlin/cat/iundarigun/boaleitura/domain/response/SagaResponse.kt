package cat.iundarigun.boaleitura.domain.response

data class SagaResponse(
    val id: Long,
    val name: String,
    val totalMainTitles: Int = 0,
    val totalComplementaryTitles: Int = 0,
    val concluded: Boolean = false
)
