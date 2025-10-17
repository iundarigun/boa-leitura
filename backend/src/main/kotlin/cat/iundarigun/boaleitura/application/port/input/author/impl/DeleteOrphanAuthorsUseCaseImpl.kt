package cat.iundarigun.boaleitura.application.port.input.author.impl

import cat.iundarigun.boaleitura.application.port.input.author.DeleteOrphanAuthorsUseCase
import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DeleteOrphanAuthorsUseCaseImpl(private val authorPort: AuthorPort) : DeleteOrphanAuthorsUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        val response = authorPort.find(orphan = true)
        response.content.forEach {
            logger.info("Deleting author: ${it.id} - ${it.name}")
            runCatching { authorPort.delete(it.id) }
                .onFailure { logger.warn("Can't not delete the author", it) }
        }
    }
}