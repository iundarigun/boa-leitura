package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class UsernameOrPasswordNotMatchException : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    message = "Incorrect username or password!"
)
