package cat.iundarigun.boaleitura.application.port.input.interactor.genre

import cat.iundarigun.boaleitura.application.port.input.CreateGenreUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.exception.GenreAlreadyExistsException
import cat.iundarigun.boaleitura.exception.GenreParentNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateGenreUseCaseImpl(val genrePort: GenrePort) : CreateGenreUseCase {

    @Transactional
    override fun execute(request: GenreRequest): GenreResponse {
        if (genrePort.existsByName(request.name)) {
            throw GenreAlreadyExistsException(request.name)
        }
        request.parentGenreId?.let {
            if (!genrePort.existsById(it)) {
                throw GenreParentNotFoundException(it)
            }
        }
        return genrePort.save(request)
    }
}