package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class SagaNotFoundException(id: Long, httpStatus: HttpStatus = HttpStatus.NOT_FOUND) :
    EntityNotFoundException("Saga", id, httpStatus)