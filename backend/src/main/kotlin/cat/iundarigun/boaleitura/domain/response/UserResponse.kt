package cat.iundarigun.boaleitura.domain.response

import cat.iundarigun.boaleitura.domain.model.UserPreferences

data class UserResponse(
    val username: String,
    val preferences: UserPreferences
)
