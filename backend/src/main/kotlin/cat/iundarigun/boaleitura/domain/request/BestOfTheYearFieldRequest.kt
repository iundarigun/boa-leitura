package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.enums.BestOfTheYearFieldEnum

data class BestOfTheYearFieldRequest(
    val field: BestOfTheYearFieldEnum,
    val readingId: Long
)
