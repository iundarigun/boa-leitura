package cat.iundarigun.boaleitura.infrastructure.rest.client

import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.domain.BookInformation
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.extensions.toBookInformation
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.GoogleApiClient
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.OpenLibraryClient
import org.springframework.stereotype.Service

@Service
class BookInformationAdapter(
    private val openLibraryClient: OpenLibraryClient,
    private val googleApiClient: GoogleApiClient
) : BookInformationPort {

    override fun search(request: BookInformationRequest): List<BookInformation> {
        val searchByIsbn = openLibraryClient.searchByIsbn(request.isbn)
        if (searchByIsbn.isEmpty()) {
            val googleApiResponse = googleApiClient.searchByIsbn(request.isbn)
            return googleApiResponse.items.map { it.toBookInformation() }
        } else {
            return searchByIsbn.values.map {
                if (it.isMissingInformation() && it.getIsbn() != null) {
                    val googleApiResponse = googleApiClient.searchByIsbn(it.getIsbn()!!)
                        .items.firstOrNull()
                    it.toBookInformation(googleApiResponse)
                } else {
                    it.toBookInformation()
                }
            }
        }
    }
}