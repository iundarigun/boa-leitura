package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.enums.GenderType

data class AuthorResponse(
    val id: Long,
    val name: String,
    val gender: GenderType? = null,
    val nationality: String? = null
)
