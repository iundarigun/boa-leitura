package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.ImageMissingUseCase
import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ImageMissingUseCaseImpl(
    private val bookPort: BookPort,
    private val jobScheduler: JobScheduler,
    private val bookInformationPort: BookInformationPort
) : ImageMissingUseCase {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        val list = bookPort.findMissingImages()
        list.forEachIndexed { index, book ->
            jobScheduler.schedule(LocalDateTime.now().plusSeconds(index.toLong() * MULTIPLIER)) {
                tryToFillUrlImage(book)
            }
        }
    }

    fun tryToFillUrlImage(book: BookResponse) {
        logger.info("Trying to fill image for isbn ${book.isbn}, title ${book.title}, author ${book.author.name}")
        var updated = false
        if (!book.isbn.isNullOrBlank()) {
            bookInformationPort.searchByIsbn(BookInformationRequest(book.isbn))
                .firstOrNull { it.urlImage != null }?.let {
                    bookPort.updateUrlImages(book.id, it.urlImage!!, it.urlImageSmall)
                    updated = true
                } ?: logger.warn("Not image found for isbn ${book.isbn}")
        }
        if (updated.not()) {
            bookInformationPort.searchByTitle(book.title, book.author.name)
                .firstOrNull { it.urlImage != null }?.let {
                    bookPort.updateUrlImages(book.id, it.urlImage!!, it.urlImageSmall)
                    updated = true
            } ?: logger.warn("Not image found for title ${book.title} and author ${book.author.name}")
        }
    }

    companion object {
        private const val MULTIPLIER = 5
    }
}