package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse

interface AuthorPort {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): AuthorResponse?
    fun authorBookCount(id: Long): Int

    fun save(authorRequest: AuthorRequest, id: Long? = null): AuthorResponse
    fun delete(id: Long)
}