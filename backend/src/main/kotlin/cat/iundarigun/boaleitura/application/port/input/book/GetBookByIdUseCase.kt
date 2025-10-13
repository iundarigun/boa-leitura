package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.response.BookResponse

interface GetBookByIdUseCase {
    fun execute(id: Long): BookResponse
}