package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book-information")
class BookInformationController(
    private val bookInformationUseCase: FindBookInformationUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getBookInformation(request: BookInformationRequest): List<BookInformationResponse> {
        logger.info("getBookInformation, request=$request")
        return bookInformationUseCase.execute(request)
    }
}