package cat.iundarigun.boaleitura.controller

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.service.AuthorService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@Valid @RequestBody request: AuthorRequest): AuthorResponse {
        logger.info("createAuthor, request=$request")
        return authorService.create(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAuthor(@PathVariable id: Long): AuthorResponse {
        logger.info("getAuthor, id=$id")
        return authorService.findById(id)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAuthor(@PathVariable id: Long, @RequestBody request: AuthorRequest): AuthorResponse {
        logger.info("updateAuthor, id=$id, request=$request")
        return authorService.update(id, request)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAuthors(): PageResponse<AuthorResponse> {
        logger.info("getAuthors")
        return authorService.find()
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable id: Long) {
        logger.info("deleteAuthor, id=$id")
        return authorService.delete(id)
    }
}