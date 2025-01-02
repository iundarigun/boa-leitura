package cat.iundarigun.boaleitura.service

import cat.iundarigun.boaleitura.domain.entity.Author
import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
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