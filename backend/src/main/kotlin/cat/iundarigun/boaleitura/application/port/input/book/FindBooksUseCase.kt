package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.request.SearchBookRequest
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface FindBooksUseCase {
    fun execute(request: SearchBookRequest): PageResponse<BookSummaryResponse>
}