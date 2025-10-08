package cat.iundarigun.boaleitura.extensions

import cat.iundarigun.boaleitura.domain.entity.SagaEntity
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.SagaResponse

fun SagaRequest.toEntity(): SagaEntity =
    SagaEntity(
        name = this.name,
        totalMainTitles = this.totalMainTitles,
        totalComplementaryTitles = this.totalComplementaryTitles,
        concluded = this.concluded
    )

fun SagaEntity.toResponse(): SagaResponse =
    SagaResponse(
        id = this.id,
        name = this.name,
        totalMainTitles = this.totalMainTitles,
        totalComplementaryTitles = this.totalComplementaryTitles,
        concluded = this.concluded
    )