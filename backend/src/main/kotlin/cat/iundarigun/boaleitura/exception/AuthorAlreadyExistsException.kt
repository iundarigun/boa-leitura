package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class AuthorAlreadyExistsException(name: String) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Author $name already exists!"
)
