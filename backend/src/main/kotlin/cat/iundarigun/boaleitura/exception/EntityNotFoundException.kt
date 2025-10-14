package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

open class EntityNotFoundException(
    entity: String,
    id: Long? = null,
    httpStatus: HttpStatus = HttpStatus.NOT_FOUND
) :
    BoaLeituraBusinessException(
        httpStatus = httpStatus,
        message = "$entity ${id?.let { "with id $it" } ?: ""} not found"
    )