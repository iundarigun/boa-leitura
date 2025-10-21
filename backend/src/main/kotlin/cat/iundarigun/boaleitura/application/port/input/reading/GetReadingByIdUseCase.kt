package cat.iundarigun.boaleitura.application.port.input.reading

import cat.iundarigun.boaleitura.domain.response.ReadingResponse

interface GetReadingByIdUseCase {
    fun execute(id: Long): ReadingResponse
}