package cat.iundarigun.boaleitura.domain.model

data class User(
    val id: Long? = null,
    val username: String,
    val encryptedPassword: String,
    val userPreferences: UserPreferences = UserPreferences(),
)
