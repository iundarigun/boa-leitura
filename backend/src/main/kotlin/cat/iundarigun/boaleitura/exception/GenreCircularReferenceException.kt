package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GenreCircularReferenceException(id: Long) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The parentId $id creates a circular reference!"
)
