package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest

interface BookInformationPort {
    fun search(request: BookInformationRequest): List<BookInformation>
}