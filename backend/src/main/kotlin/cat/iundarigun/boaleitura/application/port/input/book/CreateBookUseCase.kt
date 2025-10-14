package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse

interface CreateBookUseCase {
    fun execute(request: BookRequest): BookResponse
}