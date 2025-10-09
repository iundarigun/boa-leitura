package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.AuthorRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.AuthorResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface AuthorPort {
    fun find(name: String?, pageRequest: PageRequest): PageResponse<AuthorResponse>
    fun existsByName(name: String): Boolean
    fun findByName(name: String): AuthorResponse?
    fun findById(id: Long): AuthorResponse
    fun authorBookCount(id: Long): Int

    fun save(authorRequest: AuthorRequest, id: Long? = null): AuthorResponse
    fun delete(id: Long)
}