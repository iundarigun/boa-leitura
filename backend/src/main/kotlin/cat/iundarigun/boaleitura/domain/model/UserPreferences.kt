package cat.iundarigun.boaleitura.domain.model

data class UserPreferences(
    val languageTags: Map<String, String> = mapOf(),
    val formatTags: Map<String, String> = mapOf(),
    val platformTags: Map<String, String> = mapOf(),
)
