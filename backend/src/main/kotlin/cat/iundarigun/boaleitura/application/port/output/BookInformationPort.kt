package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.BookInformation

interface BookInformationPort {
    fun searchByIsbn(isbn: String): List<BookInformation>
    fun searchByTitle(title: String, author: String?): List<BookInformation>
}