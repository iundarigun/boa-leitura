package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface BookPort {
    fun find(title: String?, read: Boolean?, pageRequest: PageRequest): PageResponse<BookSummaryResponse>
}
