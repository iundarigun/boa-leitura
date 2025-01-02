package cat.iundarigun.boaleitura.domain.request

import cat.iundarigun.boaleitura.domain.GenderType

data class AuthorRequest(
    val name: String,
    val gender: GenderType,
    val nationality: String? = null
)
