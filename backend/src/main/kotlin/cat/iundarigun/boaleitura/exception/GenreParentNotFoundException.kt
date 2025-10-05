package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GenreParentNotFoundException(id: Long) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Genre parent with id $id does not exists!"
)