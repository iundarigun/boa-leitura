package cat.iundarigun.boaleitura.application.port.input

import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface FindGenresUseCase {
    fun execute(): PageResponse<GenreResponse>
}