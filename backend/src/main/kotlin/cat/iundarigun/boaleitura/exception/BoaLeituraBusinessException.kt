package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

open class BoaLeituraBusinessException(
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    message: String
) : RuntimeException(message)