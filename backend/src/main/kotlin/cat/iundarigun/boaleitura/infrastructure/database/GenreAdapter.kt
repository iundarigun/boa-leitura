package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.GenrePort
import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.exception.GenreNotFoundException
import cat.iundarigun.boaleitura.domain.extensions.merge
import cat.iundarigun.boaleitura.domain.extensions.toEntity
import cat.iundarigun.boaleitura.domain.extensions.toPageResponse
import cat.iundarigun.boaleitura.domain.extensions.toPageable
import cat.iundarigun.boaleitura.domain.extensions.toResponse
import cat.iundarigun.boaleitura.domain.extensions.toResponseWithSubGenders
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreAdapter(
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository
) : GenrePort {

    @Transactional(readOnly = true)
    override fun findLevelZero(name: String?, pageRequest: PageRequest): PageResponse<GenreResponse> {

        val specifications = Specification.allOf<GenreEntity>(
            specIsNull("parent"),
            specLikeWithOrFields(name, "name", "subGenres.name")
        )
        return genreRepository.findAll(specifications, pageRequest.toPageable())
            .map { it.toResponseWithSubGenders() }
            .toPageResponse()
    }

    @Transactional(readOnly = true)
    override fun existsByName(name: String): Boolean =
        genreRepository.existsByName(name)

    @Transactional(readOnly = true)
    override fun existsById(id: Long): Boolean =
        genreRepository.existsById(id)

    @Transactional(readOnly = true)
    override fun findByName(name: String): GenreResponse? =
        genreRepository.findByName(name)?.toResponse()

    @Transactional(readOnly = true)
    override fun existsParentIdInHierarchy(startId: Long, parentId: Long): Boolean =
        genreRepository.existsParentIdInHierarchy(startId, parentId)

    @Transactional(readOnly = true)
    override fun findById(id: Long): GenreResponse {
        val author = genreRepository.findById(id).orElseThrow { GenreNotFoundException(id) }
        return author.toResponse()
    }

    @Transactional(readOnly = true)
    override fun hasChildren(id: Long): Boolean =
        genreRepository.existsByParentId(id)

    @Transactional(readOnly = true)
    override fun genreBookCount(id: Long): Int =
        bookRepository.countByGenreId(id)

    @Transactional
    override fun save(request: GenreRequest, id: Long?): GenreResponse {
        val parent = request.parentGenreId?.let {
            genreRepository.findById(it).orElseThrow { GenreNotFoundException(it) }
        }
        if (id == null) {
            return genreRepository.save(request.toEntity(parent)).toResponse()
        }
        val genre = genreRepository.findById(id)
            .orElseThrow { GenreNotFoundException(id) }
        return genreRepository.save(genre.merge(request, parent)).toResponse()
    }

    @Transactional
    override fun delete(id: Long) {
        if (!genreRepository.existsById(id)) {
            throw GenreNotFoundException(id)
        }
        genreRepository.deleteById(id)
    }
}