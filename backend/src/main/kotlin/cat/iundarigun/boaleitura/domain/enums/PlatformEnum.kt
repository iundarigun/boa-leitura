package cat.iundarigun.boaleitura.domain.enums

enum class PlatformEnum {
    OWN,
    KINDLE,
    KOBO,
    EBIBLIO,
    BIBLION,
    UNLIMITED,
    BIBLIO,
    AUDIBLE,
    VIVLIO,
    BOOK_BEAT,
    PLAY_BOOKS;

    companion object {
        fun findValue(value:String?): PlatformEnum? {
            if (value.isNullOrBlank()) {
                return null
            }
            return PlatformEnum.entries.find { it.name == value }
        }
    }
}