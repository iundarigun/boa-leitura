package cat.iundarigun.boaleitura.application.port.input.reading

import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse

interface FindReadingsUseCase {
    fun execute(request: SearchReadingRequest): PageResponse<ReadingResponse>
}