package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.goodreads.GoodreadsImportUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/imports")
class ImportController(private val goodreadsImportUseCase: GoodreadsImportUseCase) {

    @PostMapping
    fun import(@RequestBody filePath: String) {
        goodreadsImportUseCase.execute(filePath)
    }
}