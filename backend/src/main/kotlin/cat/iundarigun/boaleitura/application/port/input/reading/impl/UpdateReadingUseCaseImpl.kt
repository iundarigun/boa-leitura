package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.UpdateReadingUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import cat.iundarigun.boaleitura.exception.ReadingAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class UpdateReadingUseCaseImpl(private val readingPort: ReadingPort) : UpdateReadingUseCase {

    override fun execute(id: Long, request: ReadingRequest): ReadingResponse {
        readingPort.findByBookIdAndDateRead(request.bookId, request.dateRead)?.let {
            if (it.id != id) {
                throw ReadingAlreadyExistsException(request.dateRead)
            }
        }
        return readingPort.save(request, id)
    }
}