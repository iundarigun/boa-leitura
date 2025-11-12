package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.enums.BestOfTheYearFieldEnum
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearFieldRequest
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse

fun BestOfTheYearResponse.toRequest(request: BestOfTheYearFieldRequest): BestOfTheYearRequest {
    return BestOfTheYearRequest(
        id = this.id,
        year = this.year,
        january = mayUpdateValue(request, BestOfTheYearFieldEnum.JANUARY, this.january?.id),
        february = mayUpdateValue(request, BestOfTheYearFieldEnum.FEBRUARY, this.february?.id),
        march = mayUpdateValue(request, BestOfTheYearFieldEnum.MARCH, this.march?.id),
        april = mayUpdateValue(request, BestOfTheYearFieldEnum.APRIL, this.april?.id),
        may = mayUpdateValue(request, BestOfTheYearFieldEnum.MAY, this.may?.id),
        june = mayUpdateValue(request, BestOfTheYearFieldEnum.JUNE, this.june?.id),
        july = mayUpdateValue(request, BestOfTheYearFieldEnum.JULY, this.july?.id),
        august = mayUpdateValue(request, BestOfTheYearFieldEnum.AUGUST, this.august?.id),
        september = mayUpdateValue(request, BestOfTheYearFieldEnum.SEPTEMBER, this.september?.id),
        october = mayUpdateValue(request, BestOfTheYearFieldEnum.OCTOBER, this.october?.id),
        november = mayUpdateValue(request, BestOfTheYearFieldEnum.NOVEMBER, this.november?.id),
        december = mayUpdateValue(request, BestOfTheYearFieldEnum.DECEMBER, this.december?.id),
        quarterOne = mayUpdateValue(request, BestOfTheYearFieldEnum.QUARTER_ONE, this.quarterOne?.id),
        quarterTwo = mayUpdateValue(request, BestOfTheYearFieldEnum.QUARTER_TWO, this.quarterTwo?.id),
        quarterThree = mayUpdateValue(request, BestOfTheYearFieldEnum.QUARTER_THREE, this.quarterThree?.id),
        quarterFour = mayUpdateValue(request, BestOfTheYearFieldEnum.QUARTER_FOUR, this.quarterFour?.id),
        firstHalf = mayUpdateValue(request, BestOfTheYearFieldEnum.FIRST_HALF, this.firstHalf?.id),
        secondHalf = mayUpdateValue(request, BestOfTheYearFieldEnum.SECOND_HALF, this.secondHalf?.id),
        bestOfTheYear = mayUpdateValue(request, BestOfTheYearFieldEnum.BEST_OF_THE_YEAR, this.bestOfTheYear?.id),
        )
}

private fun mayUpdateValue(request: BestOfTheYearFieldRequest, field: BestOfTheYearFieldEnum, currentId: Long?): Long? {
    return if (request.field == field) {
        request.readingId
    } else {
        currentId
    }
}