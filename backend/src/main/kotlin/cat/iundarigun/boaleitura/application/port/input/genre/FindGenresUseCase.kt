package cat.iundarigun.boaleitura.application.port.input.genre

import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface FindGenresUseCase {
    fun execute(): PageResponse<GenreResponse>
}