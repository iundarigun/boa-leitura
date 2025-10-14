package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface SagaPort {
    fun find(name: String?, pageRequest: PageRequest): PageResponse<SagaResponse>
    fun existsByName(name: String): Boolean
    fun findByName(name: String): SagaResponse?
    fun findById(id: Long): SagaResponse
    fun sagaBookCount(id: Long): Int
    fun existsById(id: Long): Boolean

    fun save(request: SagaRequest, id: Long? = null): SagaResponse
    fun delete(id: Long)
}