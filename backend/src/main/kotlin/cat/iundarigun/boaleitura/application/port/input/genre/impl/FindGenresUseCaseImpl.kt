package cat.iundarigun.boaleitura.application.port.input.genre.impl

import cat.iundarigun.boaleitura.application.port.input.genre.FindGenresUseCase
import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FindGenresUseCaseImpl(val genrePort: GenrePort) : FindGenresUseCase {

    @Transactional(readOnly = true)
    override fun execute(): PageResponse<GenreResponse> =
        genrePort.findLevelZero()
}