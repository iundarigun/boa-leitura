package cat.iundarigun.boaleitura.domain.enums

import java.time.Month

enum class BestOfTheYearFieldEnum(val months: List<Month>) {
    JANUARY(listOf(Month.JANUARY)),
    FEBRUARY(listOf(Month.FEBRUARY)),
    MARCH(listOf(Month.MARCH)),
    APRIL(listOf(Month.APRIL)),
    MAY(listOf(Month.MAY)),
    JUNE(listOf(Month.JUNE)),
    JULY(listOf(Month.JULY)),
    AUGUST(listOf(Month.AUGUST)),
    SEPTEMBER(listOf(Month.SEPTEMBER)),
    OCTOBER(listOf(Month.OCTOBER)),
    NOVEMBER(listOf(Month.NOVEMBER)),
    DECEMBER(listOf(Month.DECEMBER)),
    QUARTER_ONE(listOf(Month.JANUARY, Month.FEBRUARY, Month.MARCH)),
    QUARTER_TWO(listOf(Month.APRIL, Month.MAY, Month.JUNE)),
    QUARTER_THREE(listOf(Month.JULY, Month.AUGUST, Month.SEPTEMBER)),
    QUARTER_FOUR(listOf(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)),
    FIRST_HALF(listOf(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE)),
    SECOND_HALF(listOf(Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)),
    BEST_OF_THE_YEAR(listOf())
}