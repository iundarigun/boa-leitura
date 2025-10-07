package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.Size

data class GenreRequest(
    @field:Size(min = 3, max = 255)
    val name: String,
    val parentGenreId: Long? = null
)
