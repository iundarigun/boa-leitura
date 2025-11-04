package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ToBeReadAlreadyExistsException : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The book is already in the TBR!"
)
