package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse

interface UpdateBookUseCase {
    fun execute(id: Long, request: BookRequest): BookResponse
}