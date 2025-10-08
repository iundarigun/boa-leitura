package cat.iundarigun.boaleitura.application.port.input.genre

import cat.iundarigun.boaleitura.domain.response.GenreResponse

interface GetGenreByIdUseCase {
    fun execute(id: Long): GenreResponse
}