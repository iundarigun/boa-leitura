package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.exception.BookAlreadyExistsException
import cat.iundarigun.boaleitura.exception.GenreNotFoundException
import cat.iundarigun.boaleitura.exception.SagaNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class BookValidator(
    private val bookPort: BookPort,
    private val authorPort: AuthorPort,
    private val sagaPort: SagaPort,
    private val genrePort: GenrePort
) {
    fun validate(request: BookRequest, id: Long? = null) {
        if (id == null) {
            if (bookPort.existsByIsbn(request.isbn)) {
                throw BookAlreadyExistsException(request.isbn)
            }
        } else {
            bookPort.findByIsbn(request.isbn)?.let {
                if (it.id != id) {
                    throw BookAlreadyExistsException(request.isbn)
                }
            }
        }
        validateAuthor(request.authorId)
        validateGenre(request.genreId)
        request.saga?.let { saga ->
            validateSaga(saga.id)
        }
    }

    private fun validateAuthor(id: Long) {
        if (!authorPort.existsById(id)) {
            throw AuthorNotFoundException(id, HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    private fun validateGenre(id: Long) {
        if (!genrePort.existsById(id)) {
            throw GenreNotFoundException(id, HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    private fun validateSaga(id: Long) {
        if (!sagaPort.existsById(id)) {
            throw SagaNotFoundException(id, HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }
}