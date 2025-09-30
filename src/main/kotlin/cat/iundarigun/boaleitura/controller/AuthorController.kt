package cat.iundarigun.boaleitura.controller

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.service.AuthorService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@RequestBody request: AuthorRequest): AuthorResponse {
        logger.info("create author, request=$request")
        return authorService.create(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAuthor(@PathVariable id: Long): AuthorResponse {
        logger.info("get author, id=$id")
        return authorService.retrieve(id)
    }
}