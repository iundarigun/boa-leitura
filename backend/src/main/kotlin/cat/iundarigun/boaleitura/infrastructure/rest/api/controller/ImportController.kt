package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/imports")
class ImportController(private val goodreadsImportUseCase: GoodreadsImportUseCase) {

    @PostMapping("/goodreads", consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun uploadFile(@RequestParam(value = "file") file: MultipartFile): List<GoodreadsImporterRequest> {
        return goodreadsImportUseCase.execute(file.inputStream)
    }
}