package cat.iundarigun.boaleitura.application.port.input.reading.impl

import cat.iundarigun.boaleitura.application.port.input.reading.DeleteReadingUseCase
import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import org.springframework.stereotype.Component

@Component
class DeleteReadingUseCaseImpl(private val readingPort: ReadingPort) : DeleteReadingUseCase {

    override fun execute(id: Long) {
        readingPort.delete(id)
    }
}