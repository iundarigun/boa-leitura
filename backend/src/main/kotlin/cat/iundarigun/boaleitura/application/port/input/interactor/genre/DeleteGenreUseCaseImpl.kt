package cat.iundarigun.boaleitura.application.port.input.interactor.genre

import cat.iundarigun.boaleitura.application.port.input.DeleteGenreUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.exception.GenreDeleteException
import org.springframework.stereotype.Component

@Component
class DeleteGenreUseCaseImpl(private val genrePort: GenrePort) : DeleteGenreUseCase {
    override fun execute(id: Long) {
        if (genrePort.genreBookCount(id) > 0 || genrePort.hasChildren(id)) {
            throw GenreDeleteException(id)
        }
        genrePort.delete(id)
    }
}