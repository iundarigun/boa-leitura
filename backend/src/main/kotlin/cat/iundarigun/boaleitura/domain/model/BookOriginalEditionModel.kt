package cat.iundarigun.boaleitura.domain.model

import jakarta.validation.constraints.Size

data class BookOriginalEditionModel(
    @field:Size(min = 1, max = 255)
    val title: String?,
    @field:Size(min = 1, max = 200)
    val language: String?
)