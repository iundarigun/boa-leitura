package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.ReadingRequest

interface ReadingPort {
    fun createIfNotExists(request: ReadingRequest)
}