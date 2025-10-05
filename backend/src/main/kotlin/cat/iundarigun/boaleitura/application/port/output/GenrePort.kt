package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface GenrePort {
    fun find(): PageResponse<GenreResponse>
}