package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface GenrePort {
    fun find(): PageResponse<GenreResponse>
    fun existsByName(name: String): Boolean
    fun existsById(id: Long): Boolean
    fun findByName(name: String): GenreResponse?
    fun existsParentIdInHierarchy(startId: Long, parentId: Long): Boolean

    fun save(request: GenreRequest, id: Long? = null): GenreResponse
}