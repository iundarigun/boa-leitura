package cat.iundarigun.boaleitura.application.port.input.tbr

import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse

interface UpdateToBeReadUseCase {
    fun execute(id: Long, request: ToBeReadRequest): ToBeReadResponse
}