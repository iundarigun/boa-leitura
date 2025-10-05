package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.enums.GenderType
import jakarta.validation.constraints.Size

data class AuthorRequest(
    @field:Size(min = 3, max = 255)
    val name: String,
    val gender: GenderType,
    val nationality: String? = null
)
