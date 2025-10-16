package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.ReadingPort
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toReading
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReadingAdapter(
    private val bookRepository: BookRepository,
    private val readingRepository: ReadingRepository
) : ReadingPort {

    @Transactional
    override fun createIfNotExists(request: ReadingRequest) {
        readingRepository.findByBookIdAndDateRead(request.bookId, request.dateRead).orElseGet {
            val book = bookRepository.findById(request.bookId)
                .orElseThrow { AuthorNotFoundException(request.bookId) }
            readingRepository.save(request.toReading(book))
        }
    }
}