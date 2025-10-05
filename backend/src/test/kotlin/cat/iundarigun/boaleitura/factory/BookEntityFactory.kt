package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.BookEntity
import cat.iundarigun.boaleitura.infrastructure.database.entity.SagaEntity

object BookEntityFactory {
    fun build(
        author: AuthorEntity = AuthorEntityFactory.build(),
        saga: SagaEntity? = null
    ): BookEntity =
        BookEntity(
            goodreadsId = FakerConfiguration.FAKER.number().numberBetween(100_000L, 999_999L),
            title = FakerConfiguration.FAKER.book().title(),
            numberOfPages = FakerConfiguration.FAKER.number().numberBetween(50, 2_000),
            publisherYear = FakerConfiguration.FAKER.number().numberBetween(1_800, 2_025),
            isbn = FakerConfiguration.FAKER.internet().uuid(),
            isbn13 = FakerConfiguration.FAKER.internet().uuid(),
            originalLanguage = FakerConfiguration.FAKER.languageCode().iso639(),
            author = author,
            saga = saga
        )
}