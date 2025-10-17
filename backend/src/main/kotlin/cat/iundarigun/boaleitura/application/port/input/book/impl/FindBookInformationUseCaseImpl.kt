package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.FindBookInformationUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.domain.extensions.toBookInformationResponse
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse
import org.springframework.stereotype.Component

@Component
class FindBookInformationUseCaseImpl(
    private val bookInformationPort: BookInformationPort,
    private val authorPort: AuthorPort
) : FindBookInformationUseCase {
    override fun execute(request: BookInformationRequest): List<BookInformationResponse> {
        request.isbn?.let {
            return searchByIsbn(request.isbn)
        }
        request.title?.let {
            return searchByTitleAndAuthor(request.title, request.author)
        }
        return emptyList()
    }

    private fun searchByIsbn(isbn: String): List<BookInformationResponse> {
        val searchResult = bookInformationPort.searchByIsbn(isbn)
        return searchResult.map {
            val authorResponse = it.author?.let { author ->
                authorPort.createIfNotExists(author)
            }
            it.toBookInformationResponse(authorResponse)
        }
    }

    private fun searchByTitleAndAuthor(title: String, author: String?): List<BookInformationResponse> {
        val searchResult = bookInformationPort.searchByTitle(title, author)
        return searchResult.map {
            val authorResponse = it.author?.let { author ->
                authorPort.createIfNotExists(author)
            }
            it.toBookInformationResponse(authorResponse)
        }
    }
}