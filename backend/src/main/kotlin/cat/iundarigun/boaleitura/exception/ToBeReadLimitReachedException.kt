package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ToBeReadLimitReachedException(limit: Int) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The limit for the TBR ($limit) was reached"
)