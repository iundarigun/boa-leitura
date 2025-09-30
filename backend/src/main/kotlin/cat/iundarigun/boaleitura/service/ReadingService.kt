package cat.iundarigun.boaleitura.service

import cat.iundarigun.boaleitura.domain.entity.Book
import cat.iundarigun.boaleitura.domain.entity.Reading
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.repository.ReadingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReadingService(private val readingRepository: ReadingRepository) {

    @Transactional
    fun createIfNotExists(readingRequest: ReadingRequest, book: Book): Reading =
        readingRepository.findByBookIdAndDateRead(book.id, readingRequest.dateRead).orElseGet {
            readingRepository.save(readingRequest.toReading(book))
        }
}

private fun ReadingRequest.toReading(book: Book): Reading =
    Reading(
        myRating = this.myRating,
        dateRead = this.dateRead,
        book = book,
        format = this.format,
        language = this.language
    )
