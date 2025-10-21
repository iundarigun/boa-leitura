package cat.iundarigun.boaleitura.domain.enums

enum class TagEnum {
    KINDLE,
    BIBLIO,
    UNLIMITED,
    FISIC,
    SPARROW,
    AUDIOBOOK;

    companion object {
        fun findValue(bookshelf: List<String>): TagEnum? =
            TagEnum.entries.find { bookshelf.contains(it.name.lowercase()) }
    }
}