package cat.iundarigun.boaleitura.application.port.input.genre.impl

import cat.iundarigun.boaleitura.application.port.input.genre.UpdateGenreUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.exception.GenreAlreadyExistsException
import cat.iundarigun.boaleitura.exception.GenreCircularReferenceException
import cat.iundarigun.boaleitura.exception.GenreParentNotFoundException
import org.springframework.stereotype.Component

@Component
class UpdateGenreUseCaseImpl(private val genrePort: GenrePort) : UpdateGenreUseCase {

    override fun execute(id: Long, request: GenreRequest): GenreResponse {
        genrePort.findByName(request.name)?.let {
            if (it.id != id) {
                throw GenreAlreadyExistsException(request.name)
            }
        }
        request.parentGenreId?.let {
            if (!genrePort.existsById(it)) {
                throw GenreParentNotFoundException(it)
            }
            if (genrePort.existsParentIdInHierarchy(it, id)) {
                throw GenreCircularReferenceException(it)
            }
        }
        return genrePort.save(request, id)
    }
}