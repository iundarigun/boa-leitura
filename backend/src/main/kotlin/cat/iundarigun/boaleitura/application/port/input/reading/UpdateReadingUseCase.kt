package cat.iundarigun.boaleitura.application.port.input.reading

import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingResponse

interface UpdateReadingUseCase {
    fun execute(id: Long, request: ReadingRequest): ReadingResponse
}
