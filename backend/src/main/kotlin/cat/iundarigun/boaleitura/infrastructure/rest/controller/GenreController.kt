package cat.iundarigun.boaleitura.infrastructure.rest.controller

import cat.iundarigun.boaleitura.application.port.input.genre.CreateGenreUseCase
import cat.iundarigun.boaleitura.application.port.input.genre.DeleteGenreUseCase
import cat.iundarigun.boaleitura.application.port.input.genre.FindGenresUseCase
import cat.iundarigun.boaleitura.application.port.input.genre.GetGenreByIdUseCase
import cat.iundarigun.boaleitura.application.port.input.genre.UpdateGenreUseCase
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
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

@RestController
@RequestMapping("/genres")
class GenreController(
    private val findGenreUseCase: FindGenresUseCase,
    private val createGenreUseCase: CreateGenreUseCase,
    private val updateGenreUseCase: UpdateGenreUseCase,
    private val getGenreByIdUseCase: GetGenreByIdUseCase,
    private val deleteGenreUseCase: DeleteGenreUseCase
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getGenres(): PageResponse<GenreResponse> {
        logger.info("getGenres")
        return findGenreUseCase.execute()
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getGenreById(@PathVariable id: Long): GenreResponse {
        logger.info("getGenreById, id=$id")
        return getGenreByIdUseCase.execute(id)
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

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) {
        logger.info("deleteById, id=$id")
        deleteGenreUseCase.execute(id)
    }
}