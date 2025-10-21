package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.CreateReadingUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.exception.ReadingAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class CreateReadingUseCaseImpl(val readingPort: ReadingPort) : CreateReadingUseCase {

    override fun execute(request: ReadingRequest): ReadingResponse {
        if (readingPort.existsByBookIdAndDateRead(request.bookId, request.dateRead)) {
            throw ReadingAlreadyExistsException(request.dateRead)
        }
        return readingPort.save(request)
    }
}