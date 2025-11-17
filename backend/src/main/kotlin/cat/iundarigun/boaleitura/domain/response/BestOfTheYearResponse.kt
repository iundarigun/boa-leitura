package cat.iundarigun.boaleitura.domain.response

data class BestOfTheYearResponse(
    val id: Long? = null,
    val year: Int,
    val january: ReadingSummaryResponse? = null,
    val february: ReadingSummaryResponse? = null,
    val march: ReadingSummaryResponse? = null,
    val april: ReadingSummaryResponse? = null,
    val may: ReadingSummaryResponse? = null,
    val june: ReadingSummaryResponse? = null,
    val july: ReadingSummaryResponse? = null,
    val august: ReadingSummaryResponse? = null,
    val september: ReadingSummaryResponse? = null,
    val october: ReadingSummaryResponse? = null,
    val november: ReadingSummaryResponse? = null,
    val december: ReadingSummaryResponse? = null,
    val quarterOne: ReadingSummaryResponse? = null,
    val quarterTwo: ReadingSummaryResponse? = null,
    val quarterThree: ReadingSummaryResponse? = null,
    val quarterFour: ReadingSummaryResponse? = null,
    val firstHalf: ReadingSummaryResponse? = null,
    val secondHalf: ReadingSummaryResponse? = null,
    val bestOfTheYear: ReadingSummaryResponse? = null,
) {
    fun retrieveQuarterOneIds() =
        listOfNotNull(january?.id, february?.id, march?.id)

    fun retrieveQuarterTwoIds() =
        listOfNotNull(april?.id, may?.id, june?.id)

    fun retrieveQuarterThreeIds() =
        listOfNotNull(july?.id, august?.id, september?.id)

    fun retrieveQuarterFourIds() =
        listOfNotNull(october?.id, november?.id, december?.id)

    fun retrieveFirstHalfIds() =
        listOfNotNull(quarterOne?.id, quarterTwo?.id)

    fun retrieveSecondHalfIds() =
        listOfNotNull(quarterThree?.id, quarterFour?.id)

    fun retrieveBestOfTheYearIds() =
        listOfNotNull(firstHalf?.id, secondHalf?.id)
}
