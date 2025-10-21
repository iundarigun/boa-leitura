package cat.iundarigun.boaleitura.application.port.input.reading

import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingResponse

interface CreateReadingUseCase {
    fun execute(request: ReadingRequest): ReadingResponse
}