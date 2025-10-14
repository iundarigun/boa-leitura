package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class BookAlreadyExistsException(isbn: String) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Book $isbn already exists!"
)
