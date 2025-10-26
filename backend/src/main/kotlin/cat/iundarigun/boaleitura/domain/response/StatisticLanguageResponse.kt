package cat.iundarigun.boaleitura.domain.response

data class StatisticLanguageResponse(
    val totalByLanguage: Map<String, Int>,
    val originalPerTranslated: Map<String, Int>,
    val totalByTranslated: Map<String, Int>,
    val totalByOriginal: Map<String, Int>
)
