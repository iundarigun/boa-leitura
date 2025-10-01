package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class AuthorDeleteException(id:Long): BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Author $id can not be deleted!")
