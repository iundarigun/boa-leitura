package cat.iundarigun.boaleitura.domain.enums

enum class LanguageEnum(val label: String) {
    CATALA("català"),
    ESPANOL("español"),
    PORTUGUES("português"),
    ENGLISH("english");

    companion object {
        fun findValue(bookshelf: List<String>): LanguageEnum? =
            LanguageEnum.entries.find { bookshelf.contains(it.label) }
    }
}