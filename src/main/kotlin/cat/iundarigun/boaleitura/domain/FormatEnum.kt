package cat.iundarigun.boaleitura.domain

enum class FormatEnum {
    KINDLE,
    BIBLIO,
    UNLIMITED,
    FISIC,
    SPARROW;

    companion object {
        fun findValue(bookshelf: List<String>): FormatEnum? =
            FormatEnum.entries.find { bookshelf.contains(it.name.lowercase()) }
    }
}