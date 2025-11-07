package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.AssertTrue
import org.springframework.web.multipart.MultipartFile

data class GoodreadsRequest(
    val file: MultipartFile,
    val read: Boolean,
    val tbr: Boolean
) {
    @AssertTrue(message = "Mark read and/or tbr")
    fun isImportable(): Boolean = read || tbr
}
