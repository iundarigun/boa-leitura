package cat.iundarigun.boaleitura.application.port.input.tbr

import cat.iundarigun.boaleitura.domain.request.SearchToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse

interface FindToBeReadUseCase {
    fun execute(request: SearchToBeReadRequest): PageResponse<ToBeReadResponse>
}