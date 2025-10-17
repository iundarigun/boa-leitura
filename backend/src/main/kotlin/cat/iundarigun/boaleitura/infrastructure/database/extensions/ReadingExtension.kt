package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.enums.FormatEnum
import cat.iundarigun.boaleitura.domain.enums.PlatformEnum
import cat.iundarigun.boaleitura.domain.enums.TagEnum
import cat.iundarigun.boaleitura.domain.request.ReadingRequest
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity

fun ReadingRequest.toReading(book: BookEntity): ReadingEntity =
    ReadingEntity(
        myRating = this.myRating,
        dateRead = this.dateRead,
        book = book,
        format = this.toFormat(),
        platform = this.toPlatform(),
        language = this.language
    )

private fun ReadingRequest.toFormat(): FormatEnum? {
    if (this.format == null) {
        return null
    }
    return when (this.format) {
        TagEnum.FISIC -> FormatEnum.PRINTED
        TagEnum.AUDIOBOOK -> FormatEnum.AUDIOBOOK
        else -> FormatEnum.EBOOK
    }
}

private fun ReadingRequest.toPlatform(): PlatformEnum? {
    if (this.format == null) {
        return null
    }
    return when (this.format) {
        TagEnum.FISIC -> PlatformEnum.OWN
        TagEnum.AUDIOBOOK -> PlatformEnum.AUDIBLE
        TagEnum.KINDLE -> PlatformEnum.KINDLE
        TagEnum.UNLIMITED -> PlatformEnum.UNLIMITED
        TagEnum.BIBLIO -> if (this.language == "pt") PlatformEnum.BIBLION else PlatformEnum.EBIBLIO
        else -> PlatformEnum.EBOOK
    }
}