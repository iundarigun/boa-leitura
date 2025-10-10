package cat.iundarigun.boaleitura.infrastructure.rest.controller

import cat.iundarigun.boaleitura.application.port.input.book.FindBooksUseCase
import cat.iundarigun.boaleitura.domain.request.SearchBookRequest
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    private val findBooksUseCase: FindBooksUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getBooks(request: SearchBookRequest): PageResponse<BookSummaryResponse> {
        logger.info("getBooks, request=$request")
        return findBooksUseCase.execute(request)
    }
}