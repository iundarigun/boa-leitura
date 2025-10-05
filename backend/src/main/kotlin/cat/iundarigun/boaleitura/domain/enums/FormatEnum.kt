package cat.iundarigun.boaleitura.domain.enums

enum class FormatEnum {
    KINDLE,
    BIBLIO,
    UNLIMITED,
    FISIC,
    SPARROW,
    AUDIOBOOK;

    companion object {
        fun findValue(bookshelf: List<String>): FormatEnum? =
            FormatEnum.entries.find { bookshelf.contains(it.name.lowercase()) }
    }
}