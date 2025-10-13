package cat.iundarigun.boaleitura.infrastructure.rest.client

import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.extensions.toBookInformation
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.OpenLibraryClient
import org.springframework.stereotype.Service

@Service
class BookInformationAdapter(private val openLibraryClient: OpenLibraryClient) : BookInformationPort {

    override fun search(request: BookInformationRequest): List<BookInformation> {
        val searchByIsbn = openLibraryClient.searchByIsbn(request.isbn)
        return searchByIsbn.values.map { it.toBookInformation() }
    }
}