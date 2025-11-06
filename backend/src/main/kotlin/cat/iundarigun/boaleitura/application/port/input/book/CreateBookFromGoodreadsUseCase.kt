package cat.iundarigun.boaleitura.application.port.input.book

import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse

interface CreateBookFromGoodreadsUseCase {
    fun execute(record: GoodreadsImporterRequest, userPreferences: UserPreferences): BookResponse
}