package cat.iundarigun.boaleitura.application.port.input.genre.impl

import cat.iundarigun.boaleitura.application.port.input.genre.FindGenresUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.request.SearchGenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.springframework.stereotype.Component

@Component
class FindGenresUseCaseImpl(val genrePort: GenrePort) : FindGenresUseCase {

    override fun execute(request: SearchGenreRequest): PageResponse<GenreResponse> =
        genrePort.findLevelZero(request.name, request.toPageRequest())
}