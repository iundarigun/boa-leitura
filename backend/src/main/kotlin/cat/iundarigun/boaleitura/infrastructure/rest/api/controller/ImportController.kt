package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/imports")
class ImportController(private val goodreadsImportUseCase: GoodreadsImportUseCase) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/goodreads", consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun uploadFile(request: GoodreadsRequest): List<GoodreadsImporterRequest> {
        logger.info("uploadFile, filename=${request.file.originalFilename}, read=${request.read}, tbr=${request.tbr}")
        return goodreadsImportUseCase.execute(request)
    }
}