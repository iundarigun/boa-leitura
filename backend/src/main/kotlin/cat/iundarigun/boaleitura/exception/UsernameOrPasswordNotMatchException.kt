package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class UsernameOrPasswordNotMatchException(
    httpStatus: HttpStatus = HttpStatus.UNAUTHORIZED
) : BoaLeituraBusinessException(
    httpStatus = httpStatus,
    message = "Incorrect username or password!"
)
