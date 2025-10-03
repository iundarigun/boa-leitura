package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.domain.entity.Author
import cat.iundarigun.boaleitura.domain.entity.Book
import cat.iundarigun.boaleitura.domain.entity.Saga

object BookFactory {
    fun build(
        author: Author = AuthorFactory.build(),
        saga: Saga? = null
    ): Book =
        Book(
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