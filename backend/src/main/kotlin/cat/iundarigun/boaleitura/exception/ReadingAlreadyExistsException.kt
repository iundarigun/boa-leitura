package cat.iundarigun.boaleitura.exception

import org.springframework.http.HttpStatus
import java.time.LocalDate

class ReadingAlreadyExistsException(dateRead: LocalDate) : BoaLeituraBusinessException(
    httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    message = "The Reading $dateRead already exists!"
)
