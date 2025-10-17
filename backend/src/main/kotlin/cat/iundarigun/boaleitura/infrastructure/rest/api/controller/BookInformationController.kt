package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.application.port.input.book.ImageMissingUseCase
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/book-information")
class BookInformationController(
    private val bookInformationUseCase: FindBookInformationUseCase,
    private val missingUseCase: ImageMissingUseCase,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getBookInformation(@Valid request: BookInformationRequest): List<BookInformationResponse> {
        logger.info("getBookInformation, request=$request")
        return bookInformationUseCase.execute(request)
    }

    @PostMapping("images")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun missingImages() {
        logger.info("missingImages")
        missingUseCase.execute()
    }
}