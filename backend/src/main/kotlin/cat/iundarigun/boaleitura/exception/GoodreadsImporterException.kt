package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus

class GoodreadsImporterException(message: String = "Got importer error") :
    BoaLeituraBusinessException(httpStatus = HttpStatus.UNPROCESSABLE_ENTITY, message = message)