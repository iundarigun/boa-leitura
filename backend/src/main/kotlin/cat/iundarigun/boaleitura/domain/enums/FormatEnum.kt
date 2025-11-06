package cat.iundarigun.boaleitura.domain.enums

enum class FormatEnum {
    PRINTED,
    EBOOK,
    AUDIOBOOK;

    companion object {
        fun findValue(value:String?): FormatEnum? {
            if (value.isNullOrBlank()) {
                return null
            }
            return FormatEnum.entries.find { it.name == value }
        }
    }
}