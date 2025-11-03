package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ToBeReadNotFoundException(id: Long?, httpStatus: HttpStatus = HttpStatus.NOT_FOUND) :
    EntityNotFoundException("TBR entry", id, httpStatus)