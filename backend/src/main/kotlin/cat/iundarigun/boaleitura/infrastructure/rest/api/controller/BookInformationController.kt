package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.application.port.input.book.ImageMissingUseCase
import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book-information")
class BookInformationController(
    private val bookInformationUseCase: FindBookInformationUseCase,
    private val missingUseCase: ImageMissingUseCase,
    private val bookInformationPort: BookInformationPort,
    private val bookPort: BookPort
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getBookInformation(request: BookInformationRequest): List<BookInformationResponse> {
        logger.info("getBookInformation, request=$request")
        return bookInformationUseCase.execute(request)
    }

    @GetMapping("test")
    fun test(@RequestParam id: Long): Any {
        val book = bookPort.findById(id)
        return bookInformationPort.searchByTitle(
            title = book.title,
            author = book.author.name)
    }

    @PostMapping("images")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun missingImages() {
        logger.info("missingImages")
        missingUseCase.execute()
    }
}