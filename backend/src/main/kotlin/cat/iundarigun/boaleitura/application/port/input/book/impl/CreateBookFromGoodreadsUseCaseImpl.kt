package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.CreateBookFromGoodreadsUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.extensions.toBookRequest
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.exception.BoaLeituraBusinessException
import org.springframework.stereotype.Component

@Component
class CreateBookFromGoodreadsUseCaseImpl(
    private val authorPort: AuthorPort,
    private val bookPort: BookPort,
) : CreateBookFromGoodreadsUseCase {

    override fun execute(record: GoodreadsImporterRequest, userPreferences: UserPreferences): BookResponse {
        val request = record.toBookRequest(userPreferences)
        bookPort.findByGoodreadsId(request.goodreadsId)?.let {
            return it
        }
        if (!request.isbn.isNullOrBlank()) {
            bookPort.findByIsbn(request.isbn)?.let {
                return it
            }
            val author = authorPort.createIfNotExists(record.author)
            return bookPort.save(request.copy(authorId = author.id))
        }
        throw BoaLeituraBusinessException(message = " Can't not verify the book $request")
    }
}