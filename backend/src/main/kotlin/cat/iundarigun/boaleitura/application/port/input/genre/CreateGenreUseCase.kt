package cat.iundarigun.boaleitura.application.port.input.genre

import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse

interface CreateGenreUseCase {
    fun execute(request: GenreRequest): GenreResponse
}