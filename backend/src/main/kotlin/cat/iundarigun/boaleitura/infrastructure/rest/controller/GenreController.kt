package cat.iundarigun.boaleitura.infrastructure.rest.controller

import cat.iundarigun.boaleitura.application.port.input.CreateGenreUseCase
import cat.iundarigun.boaleitura.application.port.input.FindGenresUseCase
import cat.iundarigun.boaleitura.application.port.input.UpdateGenreUseCase
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
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
@RequestMapping("/genres")
class GenreController(
    private val findGenreUseCase: FindGenresUseCase,
    private val createGenreUseCase: CreateGenreUseCase,
    private val updateGenreUseCase: UpdateGenreUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getGenres(): PageResponse<GenreResponse> {
        logger.info("getGenres")
        return findGenreUseCase.execute()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGenre(@Valid @RequestBody request: GenreRequest): GenreResponse {
        logger.info("createGenre, request=$request")
        return createGenreUseCase.execute(request)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateGenre(@PathVariable id: Long, @Valid @RequestBody request: GenreRequest): GenreResponse {
        logger.info("updateGenre, id=$id, request=$request")
        return updateGenreUseCase.execute(id, request)
    }
}