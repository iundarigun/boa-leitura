package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class UserNotFoundException : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    message = "User not found!"
)
