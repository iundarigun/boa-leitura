package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.book.CreateBookUseCase
import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.application.port.input.book.FindBooksUseCase
import cat.iundarigun.boaleitura.application.port.input.book.GetBookByIdUseCase
import cat.iundarigun.boaleitura.application.port.input.book.UpdateBookUseCase
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.request.SearchBookRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    private val findBooksUseCase: FindBooksUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val bookInformationUseCase: FindBookInformationUseCase,
    private val createBookUseCase: CreateBookUseCase,
    private val updateBookUseCase: UpdateBookUseCase,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getBooks(@Valid request: SearchBookRequest): PageResponse<BookSummaryResponse> {
        logger.info("getBooks, request=$request")
        return findBooksUseCase.execute(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getBookById(@PathVariable id: Long): BookResponse {
        logger.info("getBookById, id=$id")
        return getBookByIdUseCase.execute(id)
    }

    @GetMapping("/information")
    fun getBookInformation(request: BookInformationRequest): List<BookInformationResponse> {
        logger.info("getBookInformation, request=$request")
        return bookInformationUseCase.execute(request)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@Valid @RequestBody request: BookRequest): BookResponse {
        logger.info("createBook, request=$request")
        return createBookUseCase.execute(request)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@PathVariable id: Long, @Valid @RequestBody request: BookRequest): BookResponse {
        logger.info("updateBook, id$id, request=$request")
        return updateBookUseCase.execute(id, request)
    }
}