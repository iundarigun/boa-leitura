package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class SagaAlreadyExistsException(name: String) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Saga $name already exists!"
)
