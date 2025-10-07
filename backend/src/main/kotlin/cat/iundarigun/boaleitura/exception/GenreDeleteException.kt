package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GenreDeleteException(id: Long) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Genre $id can not be deleted!"
)
