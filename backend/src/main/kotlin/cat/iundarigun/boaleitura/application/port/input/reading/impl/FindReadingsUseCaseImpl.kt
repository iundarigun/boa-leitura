package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.FindReadingsUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.SearchReadingRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ReadingSummaryResponse
import org.springframework.stereotype.Component

@Component
class FindReadingsUseCaseImpl(private val readingPort: ReadingPort) : FindReadingsUseCase {

    override fun execute(request: SearchReadingRequest): PageResponse<ReadingSummaryResponse> =
        readingPort.find(request.keyword, request.dateFrom, request.dateTo, request.toPageRequest())
}