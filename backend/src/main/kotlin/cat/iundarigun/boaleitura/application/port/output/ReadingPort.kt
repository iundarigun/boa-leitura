package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import java.time.LocalDate

interface ReadingPort {
    fun createIfNotExists(request: ReadingRequest)
    fun find(
        keyword: String?,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        pageRequest: PageRequest
    ): PageResponse<ReadingSummaryResponse>

    fun findById(id: Long): ReadingResponse
    fun existsByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): Boolean
    fun findByBookIdAndDateRead(bookId: Long, dateRead: LocalDate): ReadingResponse?
    fun save(request: ReadingRequest, id: Long? = null): ReadingResponse
    fun delete(id: Long)
}