package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class ReadingNotMatchException :
    BoaLeituraBusinessException(
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
        message = "Reading is not allowed on this action"
    )