package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadReorderRequest
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse

interface ToBeReadPort {
    fun find(
        keyword: String? = null,
        bought: Boolean? = null,
        done: Boolean? = false,
        pageRequest: PageRequest
    ): PageResponse<ToBeReadResponse>

    fun existsByBook(bookId: Long): Boolean
    fun save(request: ToBeReadRequest, id: Long? = null): ToBeReadResponse
    fun reorder(id: Long, request: ToBeReadReorderRequest)
    fun update(id: Long, request: Map<String, Any>)
    fun findByBook(bookId: Long): ToBeReadResponse?
    fun findById(id: Long): ToBeReadResponse
    fun delete(id: Long)
}
