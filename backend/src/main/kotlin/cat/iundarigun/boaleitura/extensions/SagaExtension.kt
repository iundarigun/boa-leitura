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

fun SagaEntity.toResponse(processBooks: Boolean = false): SagaResponse {
    val mainTitles = if (processBooks) {
        this.books.filter { it.sagaMainTitle ?: false }.map { it.title }
    } else {
        null
    }
    val complementaryTitles = if (processBooks) {
        this.books.filter { it.sagaMainTitle?.not() ?: false }.map { it.title }
    } else {
        null
    }
    return SagaResponse(
        id = this.id,
        name = this.name,
        totalMainTitles = this.totalMainTitles,
        totalComplementaryTitles = this.totalComplementaryTitles,
        concluded = this.concluded,
        mainTitles = mainTitles,
        complementaryTitles = complementaryTitles
    )
}

fun SagaEntity.merge(request: SagaRequest): SagaEntity {
    name = request.name
    totalMainTitles = request.totalMainTitles
    totalComplementaryTitles = request.totalComplementaryTitles
    concluded = request.concluded
    return this
}