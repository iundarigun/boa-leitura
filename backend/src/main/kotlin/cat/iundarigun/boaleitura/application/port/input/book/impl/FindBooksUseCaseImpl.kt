package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.FindBooksUseCase
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.SearchBookRequest
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.springframework.stereotype.Component

@Component
class FindBooksUseCaseImpl(private val bookPort: BookPort) : FindBooksUseCase {
    override fun execute(request: SearchBookRequest): PageResponse<BookSummaryResponse> =
        bookPort.find(request.keyword, request.read, request.toPageRequest())
}