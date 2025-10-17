package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.author.CreateAuthorUseCase
import cat.iundarigun.boaleitura.application.port.input.author.DeleteAuthorUseCase
import cat.iundarigun.boaleitura.application.port.input.author.DeleteOrphanAuthorsUseCase
import cat.iundarigun.boaleitura.application.port.input.author.FindAuthorsUseCase
import cat.iundarigun.boaleitura.application.port.input.author.GetAuthorByIdUseCase
import cat.iundarigun.boaleitura.application.port.input.author.UpdateAuthorUseCase
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.request.SearchAuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
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

@Suppress("LongParameterList")
@RestController
@RequestMapping("/authors")
class AuthorController(
    private val createAuthorUseCase: CreateAuthorUseCase,
    private val updateAuthorUseCase: UpdateAuthorUseCase,
    private val deleteAuthorUseCase: DeleteAuthorUseCase,
    private val getAuthorByIdUseCase: GetAuthorByIdUseCase,
    private val findAuthorsUseCase: FindAuthorsUseCase,
    private val deleteOrphanAuthorsUseCase: DeleteOrphanAuthorsUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@Valid @RequestBody request: AuthorRequest): AuthorResponse {
        logger.info("createAuthor, request=$request")
        return createAuthorUseCase.execute(request)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAuthorById(@PathVariable id: Long): AuthorResponse {
        logger.info("getAuthorById, id=$id")
        return getAuthorByIdUseCase.execute(id)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAuthor(@PathVariable id: Long, @Valid @RequestBody request: AuthorRequest): AuthorResponse {
        logger.info("updateAuthor, id=$id, request=$request")
        return updateAuthorUseCase.execute(id, request)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAuthors(@Valid request: SearchAuthorRequest): PageResponse<AuthorResponse> {
        logger.info("getAuthors, request=$request")
        return findAuthorsUseCase.execute(request)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable id: Long) {
        logger.info("deleteAuthor, id=$id")
        return deleteAuthorUseCase.execute(id)
    }

    @DeleteMapping("orphans")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrphanAuthor() {
        logger.info("deleteOrphanAuthor")
        return deleteOrphanAuthorsUseCase.execute()
    }
}