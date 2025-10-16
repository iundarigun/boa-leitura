package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.AuthorPort
import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.extensions.merge
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageResponse
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toPageable
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.utils.specLike
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorAdapter(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) : AuthorPort {

    @Transactional(readOnly = true)
    override fun existsByName(name: String): Boolean =
        authorRepository.existsByName(name)

    @Transactional
    override fun save(authorRequest: AuthorRequest, id: Long?): AuthorResponse {
        if (id == null) {
            return authorRepository.save(authorRequest.toEntity()).toResponse()
        }
        val author = authorRepository.findById(id)
            .orElseThrow { AuthorNotFoundException(id) }

        return authorRepository.save(author.merge(authorRequest)).toResponse()
    }

    @Transactional(readOnly = true)
    override fun findByName(name: String): AuthorResponse? =
        authorRepository.findByName(name)?.toResponse()

    @Transactional
    override fun createIfNotExists(name: String): AuthorResponse {
        val author = authorRepository.findByName(name) ?: authorRepository.save(AuthorEntity(name = name))
        return author.toResponse()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): AuthorResponse {
        val author = authorRepository.findById(id)
            .orElseThrow { AuthorNotFoundException(id) }

        return author.toResponse()
    }

    @Transactional(readOnly = true)
    override fun existsById(id: Long): Boolean =
        authorRepository.existsById(id)

    @Transactional(readOnly = true)
    override fun find(name: String?, pageRequest: PageRequest): PageResponse<AuthorResponse> =
        authorRepository.findAll(specLike(name, "name"), pageRequest.toPageable())
                .map(AuthorEntity::toResponse)
                .toPageResponse()

    @Transactional(readOnly = true)
    override fun authorBookCount(id: Long): Int =
        bookRepository.countByAuthorId(id)

    @Transactional
    override fun delete(id: Long) {
        if (!authorRepository.existsById(id)) {
            throw AuthorNotFoundException(id)
        }
        authorRepository.deleteById(id)
    }
}