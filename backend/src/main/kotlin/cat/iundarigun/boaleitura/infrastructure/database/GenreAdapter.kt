package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.exception.GenreNotFoundException
import cat.iundarigun.boaleitura.extensions.toEntity
import cat.iundarigun.boaleitura.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreAdapter(
    private val genreRepository: GenreRepository
) : GenrePort {

    @Transactional(readOnly = true)
    override fun find(): PageResponse<GenreResponse> {
        val genres = genreRepository.findAll()
            .map(GenreEntity::toResponse)

        return PageResponse(
            content = genres,
            page = 1,
            totalPages = 1
        )
    }

    @Transactional(readOnly = true)
    override fun existsByName(name: String): Boolean =
        genreRepository.existsByName(name)

    @Transactional(readOnly = true)
    override fun existsById(id: Long): Boolean =
        genreRepository.existsById(id)

    override fun save(request: GenreRequest): GenreResponse {
        val parent = request.parentGenreId?.let {
            genreRepository.findById(it).orElseThrow { GenreNotFoundException(it) }
        }
        return genreRepository.save(request.toEntity(parent)).toResponse()
    }
}