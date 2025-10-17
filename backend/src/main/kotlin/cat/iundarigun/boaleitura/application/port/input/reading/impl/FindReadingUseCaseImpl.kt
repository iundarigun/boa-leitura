package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.FindReadingUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import org.springframework.stereotype.Component

@Component
class FindReadingUseCaseImpl(private val readingPort: ReadingPort) : FindReadingUseCase {

    override fun execute(request: SearchReadingRequest): PageResponse<ReadingResponse> =
        readingPort.find(request.keyWord, request.dateFrom, request.dateTo, request.toPageRequest())
}