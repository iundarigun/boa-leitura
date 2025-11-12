package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.request.BestOfTheYearRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import cat.iundarigun.boaleitura.infrastructure.database.entity.BestOfTheYearEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity

fun BestOfTheYearEntity.toResponse(): BestOfTheYearResponse =
    BestOfTheYearResponse(
        id = this.id,
        year = this.year,
        january = this.january?.toSummaryResponse(),
        february = this.february?.toSummaryResponse(),
        march = this.march?.toSummaryResponse(),
        april = this.april?.toSummaryResponse(),
        may = this.may?.toSummaryResponse(),
        june = this.june?.toSummaryResponse(),
        july = this.july?.toSummaryResponse(),
        august = this.august?.toSummaryResponse(),
        september = this.september?.toSummaryResponse(),
        october = this.october?.toSummaryResponse(),
        november = this.november?.toSummaryResponse(),
        december = this.december?.toSummaryResponse(),
        quarterOne = this.quarterOne?.toSummaryResponse(),
        quarterTwo = this.quarterTwo?.toSummaryResponse(),
        quarterThree = this.quarterThree?.toSummaryResponse(),
        quarterFour = this.quarterFour?.toSummaryResponse(),
        firstHalf = this.firstHalf?.toSummaryResponse(),
        secondHalf = this.secondHalf?.toSummaryResponse(),
        bestOfTheYear = this.bestOfTheYear?.toSummaryResponse(),
    )

fun BestOfTheYearRequest.toEntity(getEntity: (Long) -> ReadingEntity): BestOfTheYearEntity =
    BestOfTheYearEntity(year = this.year).merge(this, getEntity)

fun BestOfTheYearEntity.merge(request: BestOfTheYearRequest, getEntity: (Long) -> ReadingEntity): BestOfTheYearEntity {
    this.january = request.january?.let { getEntity(it) }
    this.february = request.february?.let { getEntity(it) }
    this.march = request.march?.let { getEntity(it) }
    this.april = request.april?.let { getEntity(it) }
    this.may = request.may?.let { getEntity(it) }
    this.june = request.june?.let { getEntity(it) }
    this.july = request.july?.let { getEntity(it) }
    this.august = request.august?.let { getEntity(it) }
    this.september = request.september?.let { getEntity(it) }
    this.october = request.october?.let { getEntity(it) }
    this.november = request.november?.let { getEntity(it) }
    this.december = request.december?.let { getEntity(it) }
    this.quarterOne = request.quarterOne?.let { getEntity(it) }
    this.quarterTwo = request.quarterTwo?.let { getEntity(it) }
    this.quarterThree = request.quarterThree?.let { getEntity(it) }
    this.quarterFour = request.quarterFour?.let { getEntity(it) }
    this.firstHalf = request.firstHalf?.let { getEntity(it) }
    this.secondHalf = request.secondHalf?.let { getEntity(it) }
    this.bestOfTheYear = request.bestOfTheYear?.let { getEntity(it) }
    return this
}