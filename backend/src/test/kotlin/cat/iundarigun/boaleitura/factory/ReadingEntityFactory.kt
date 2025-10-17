package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ReadingEntity
import cat.iundarigun.boaleitura.domain.enums.TagEnum
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

object ReadingEntityFactory {
    fun build(book: BookEntity): ReadingEntity =
        ReadingEntity(
            myRating = FakerConfiguration.FAKER.number().numberBetween(1, 5),
            dateRead = LocalDate.ofInstant(
                FakerConfiguration.FAKER.timeAndDate().past(365 * 5, TimeUnit.DAYS),
                ZoneOffset.UTC
            ),
            book = book,
            format = FakerConfiguration.FAKER.options().option(TagEnum::class.java),
            language = FakerConfiguration.FAKER.languageCode().iso639()
        )
}