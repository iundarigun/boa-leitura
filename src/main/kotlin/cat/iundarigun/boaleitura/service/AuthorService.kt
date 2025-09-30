package cat.iundarigun.boaleitura.service

import cat.iundarigun.boaleitura.domain.entity.Author
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.exception.AuthorNotFoundException
import cat.iundarigun.boaleitura.repository.AuthorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    @Transactional
    fun create(authorRequest: AuthorRequest): AuthorResponse {
        return authorRepository.save(authorRequest.toEntity()).toResponse()
    }

    @Transactional
    fun createIfNotExists(name: String): Author {
        return authorRepository.findByName(name).orElseGet {
            authorRepository.save(Author(name = name))
        }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): AuthorResponse =
        authorRepository.findById(id)
            .orElseThrow { AuthorNotFoundException(id) }
            .toResponse()

    @Transactional
    fun update(id: Long, request: AuthorRequest): AuthorResponse {
        val author = authorRepository.findById(id)
            .orElseThrow { AuthorNotFoundException(id) }

        return authorRepository.save(author.merge(request)).toResponse()
    }

    @Transactional(readOnly = true)
    fun find(): PageResponse<AuthorResponse> {
        val authors = authorRepository.findAll()
            .map(Author::toResponse)
        return PageResponse(
            list = authors,
            page = 1,
            totalPages = 1
        )
    }

}

private fun Author.merge(request: AuthorRequest): Author {
    name = request.name
    gender = request.gender
    nationality = request.nationality
    return this
}

private fun AuthorRequest.toEntity(): Author =
    Author(
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )

private fun Author.toResponse(): AuthorResponse =
    AuthorResponse(
        id = this.id,
        name = this.name,
        gender = this.gender,
        nationality = this.nationality
    )