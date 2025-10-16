package cat.iundarigun.boaleitura.domain.enums

enum class LanguageEnum(val label: String, val value: String) {
    CATALA("català", "ca"),
    ESPANOL("español", "es"),
    PORTUGUES("português", "pt"),
    ENGLISH("english", "en");

    companion object {
        fun findValue(bookshelf: List<String>): LanguageEnum? =
            LanguageEnum.entries.find { bookshelf.contains(it.label) }
    }
}