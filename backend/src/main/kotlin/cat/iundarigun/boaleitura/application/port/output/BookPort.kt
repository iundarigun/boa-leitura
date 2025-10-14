package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.BookResponse
import cat.iundarigun.boaleitura.domain.response.BookSummaryResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse

interface BookPort {
    fun find(title: String?, read: Boolean?, pageRequest: PageRequest): PageResponse<BookSummaryResponse>
    fun findById(id: Long): BookResponse
    fun existsByIsbn(isbn: String): Boolean
    fun findByIsbn(isbn: String): BookResponse?
    fun save(request: BookRequest, id: Long? = null): BookResponse
    fun readingCount(id: Long): Int
    fun delete(id: Long)
    fun findIsbnMissingImages(): List<String>
    fun updateUrlImages(id: Long, urlImage: String, urlImageSmall: String?)
}
