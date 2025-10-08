package cat.iundarigun.boaleitura.application.port.input.genre

import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse

interface UpdateGenreUseCase {
    fun execute(id: Long, request: GenreRequest): GenreResponse
}
