package cat.iundarigun.boaleitura.application.port.input.book.impl

import cat.iundarigun.boaleitura.application.port.input.book.ImageMissingUseCase
import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.application.port.output.BookPort
import cat.iundarigun.boaleitura.domain.request.BookInformationRequest
import cat.iundarigun.boaleitura.exception.BookNotFoundException
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
        val list = bookPort.findIsbnMissingImages()
        list.forEachIndexed { index, isbn ->
            jobScheduler.schedule(LocalDateTime.now().plusMinutes(1).plusSeconds(index.toLong())) {
                tryToFillUrlImage(isbn)
            }
        }
    }

    fun tryToFillUrlImage(isbn: String) {
        logger.info("Trying to fill image $isbn")
        val result = bookInformationPort.search(BookInformationRequest(isbn))
            .firstOrNull { it.urlImage != null }

        if (result == null) {
            logger.warn("Not image found for isbn $isbn")
        } else {
            val book = bookPort.findByIsbn(isbn) ?: throw BookNotFoundException()
            bookPort.updateUrlImages(book.id, result.urlImage!!, result.urlImageSmall)
        }
    }
}