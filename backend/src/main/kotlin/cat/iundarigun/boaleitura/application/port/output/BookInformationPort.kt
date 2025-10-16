package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest

interface BookInformationPort {
    fun searchByIsbn(request: BookInformationRequest): List<BookInformation>
    fun searchByTitle(title: String, author: String): List<BookInformation>
}