package cat.iundarigun.boaleitura.application.port.input.genre.impl

import cat.iundarigun.boaleitura.application.port.input.genre.GetGenreByIdUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import org.springframework.stereotype.Component

@Component
class GetGenreByIdUseCaseImpl(private val genrePort: GenrePort) : GetGenreByIdUseCase {
    override fun execute(id: Long): GenreResponse =
        genrePort.findById(id)
}