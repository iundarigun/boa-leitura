package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

open class EntityNotFoundException(entity: String, id: Long? = null) :
    BoaLeituraBusinessException(
        httpStatus = HttpStatus.NOT_FOUND,
        message = "$entity ${id?.let { "with id $it" }} not found"
    )