package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.enums.SagaStatusEnum

data class SagaResponse(
    val id: Long,
    val name: String,
    val sagaStatus: SagaStatusEnum? = null,
    val totalMainTitles: Int = 0,
    val totalComplementaryTitles: Int = 0,
    val concluded: Boolean = false,
    val mainTitles: List<String>? = null,
    val complementaryTitles: List<String>? = null
)
