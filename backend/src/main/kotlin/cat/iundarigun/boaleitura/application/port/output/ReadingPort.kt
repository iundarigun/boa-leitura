package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import java.time.LocalDate

interface ReadingPort {
    fun createIfNotExists(request: ReadingRequest)
    fun find(
        keyword: String?,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        pageRequest: PageRequest
    ): PageResponse<ReadingResponse>
}