package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.domain.extensions.toBookInformationResponse
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import org.springframework.stereotype.Component

@Component
class FindBookInformationUseCaseImpl(
    private val bookInformationPort: BookInformationPort,
    private val authorPort: AuthorPort
) : FindBookInformationUseCase {
    override fun execute(request: BookInformationRequest): List<BookInformationResponse> {
        val searchResult = bookInformationPort.searchByIsbn(request)
        return searchResult.map {
            val authorResponse = it.author?.let { author ->
                authorPort.find(author, PageRequest()).content.firstOrNull()
            }
            it.toBookInformationResponse(authorResponse)
        }
    }
}