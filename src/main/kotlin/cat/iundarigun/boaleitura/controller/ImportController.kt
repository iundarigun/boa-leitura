package cat.iundarigun.boaleitura.controller

import cat.iundarigun.boaleitura.service.ImportService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/imports")
class ImportController(private val importService: ImportService) {

    @PostMapping
    fun import(@RequestBody filePath: String) {
        importService.import(filePath)
    }
}