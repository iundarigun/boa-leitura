package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GenreNotFoundException(id: Long, httpStatus: HttpStatus = HttpStatus.NOT_FOUND) :
    EntityNotFoundException("Genre", id, httpStatus)