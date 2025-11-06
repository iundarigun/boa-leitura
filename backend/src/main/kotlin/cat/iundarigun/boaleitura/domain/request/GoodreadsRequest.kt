package cat.iundarigun.boaleitura.domain.request

import org.springframework.web.multipart.MultipartFile

data class GoodreadsRequest(
    val file: MultipartFile,
    val read: Boolean,
    val tbr: Boolean
)
