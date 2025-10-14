package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class BookDeleteException(id: Long) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Book $id can not be deleted!"
)
