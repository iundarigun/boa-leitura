package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("proxy-image")
class ProxyImageController {
    val restTemplate = RestTemplate()

    @GetMapping
    fun getImage(@RequestParam url:String): ResponseEntity<ByteArray> {
        return runCatching {
            val imageInBytes = restTemplate.getForObject(url, ByteArray::class.java)

            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.IMAGE_PNG
            httpHeaders.cacheControl = "no-cache, no-store, must-revalidate"

            ResponseEntity.ok()
                .headers(httpHeaders)
                .body(imageInBytes)
        }.getOrElse {
            ResponseEntity.badRequest().build()
        }
    }
}