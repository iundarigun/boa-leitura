package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class AuthorNotFoundException(id: Long, httpStatus: HttpStatus = HttpStatus.NOT_FOUND) :
    EntityNotFoundException("Author", id, httpStatus)