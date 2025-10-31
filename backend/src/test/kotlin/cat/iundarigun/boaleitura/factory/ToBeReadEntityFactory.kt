package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.ToBeReadEntity

object ToBeReadEntityFactory {
    fun build(book: BookEntity): ToBeReadEntity =
        ToBeReadEntity(
            position = FAKER.number().numberBetween(1L, 100L),
            done = FAKER.bool().bool(),
            bought = FAKER.bool().bool(),
            book = book,
            platforms = FAKER.lorem().words(FAKER.number().numberBetween(1, 5)),
            tags = FAKER.lorem().words(FAKER.number().numberBetween(1, 5)),
            notes = FAKER.lorem().paragraph(FAKER.number().numberBetween(1, 5))
        )
}