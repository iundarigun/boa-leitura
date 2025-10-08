package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class SagaDeleteException(id: Long) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Saga $id can not be deleted!"
)
