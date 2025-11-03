package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ToBeReadFieldsNotAllowedException : BoaLeituraBusinessException(
    httpStatus = HttpStatus.BAD_REQUEST,
    message = "The provided fields are not allowed!"
)
