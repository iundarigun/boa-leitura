package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ReadingNotFoundException(id: Long, httpStatus: HttpStatus = HttpStatus.NOT_FOUND) :
    EntityNotFoundException("Reading", id, httpStatus)