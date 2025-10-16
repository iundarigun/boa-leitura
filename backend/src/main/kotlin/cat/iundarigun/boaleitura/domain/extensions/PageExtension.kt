package cat.iundarigun.boaleitura.domain.extensions

import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.data.domain.PageRequest as DataPageRequest

fun PageRequest.toPageable() =
    DataPageRequest.of(
        this.page - 1,
        this.size,
        Sort.by(if (directionAsc) Order.asc(this.order) else Order.desc(this.order)),
    )

fun <T> Page<T>.toPageResponse(): PageResponse<T> =
        PageResponse(
            content = this.content,
            totalPages = this.totalPages,
            page = this.number + 1)