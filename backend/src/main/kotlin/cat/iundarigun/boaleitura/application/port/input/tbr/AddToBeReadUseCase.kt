package cat.iundarigun.boaleitura.application.port.input.tbr

import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse

interface AddToBeReadUseCase {
    fun execute(request: ToBeReadRequest): ToBeReadResponse
}