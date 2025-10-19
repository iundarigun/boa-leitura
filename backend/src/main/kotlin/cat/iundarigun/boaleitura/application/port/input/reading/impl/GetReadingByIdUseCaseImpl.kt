package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.GetReadingByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.response.ReadingResponse
import org.springframework.stereotype.Component

@Component
class GetReadingByIdUseCaseImpl(private val readingPort: ReadingPort) : GetReadingByIdUseCase {

    override fun execute(id: Long): ReadingResponse =
        readingPort.findById(id)
}