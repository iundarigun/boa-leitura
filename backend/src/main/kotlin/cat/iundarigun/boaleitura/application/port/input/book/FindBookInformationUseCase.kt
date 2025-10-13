package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookInformationResponse

interface FindBookInformationUseCase {
    fun execute(request: BookInformationRequest): List<BookInformationResponse>
}