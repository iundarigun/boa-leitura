package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GenreAlreadyExistsException(name: String) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The genre $name already exists!"
)
