package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class UserAlreadyExistsException(username: String) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The user $username already exists!"
)
