package cat.iundarigun.boaleitura.domain.model

data class StatisticLanguage(
    val readInOriginalLanguage: Boolean,
    val language: String,
    val count: Int
)
