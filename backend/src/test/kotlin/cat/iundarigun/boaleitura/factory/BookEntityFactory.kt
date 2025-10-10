package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.domain.entity.AuthorEntity
import cat.iundarigun.boaleitura.domain.entity.BookEntity
import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import cat.iundarigun.boaleitura.domain.entity.SagaEntity
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import org.springframework.stereotype.Component

@Component
class BookEntityFactory(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {
    companion object {
        fun build(
            author: AuthorEntity = AuthorEntityFactory.build(),
            saga: SagaEntity? = null,
            genre: GenreEntity? = null,
            sagaOrder: Double? = null,
            sagaMainTitle: Boolean? = null
        ): BookEntity =
            BookEntity(
                goodreadsId = FakerConfiguration.FAKER.number().numberBetween(100_000L, 999_999L),
                title = FakerConfiguration.FAKER.book().title(),
                numberOfPages = FakerConfiguration.FAKER.number().numberBetween(50, 2_000),
                publisherYear = FakerConfiguration.FAKER.number().numberBetween(1_800, 2_025),
                isbn = FakerConfiguration.FAKER.internet().uuid(),
                originalLanguage = FakerConfiguration.FAKER.languageCode().iso639(),
                author = author,
                saga = saga,
                sagaOrder = sagaOrder,
                sagaMainTitle = sagaMainTitle,
                genre = genre,
            )
    }

    fun buildAndSave(
        author: AuthorEntity? = null,
        saga: SagaEntity? = null,
        genre: GenreEntity? = null,
        sagaOrder: Double? = null,
        sagaMainTitle: Boolean? = null
    ): BookEntity {
        val book = build(
            author = author ?: authorRepository.save(AuthorEntityFactory.build()),
            saga = saga,
            genre = genre,
            sagaOrder = sagaOrder,
            sagaMainTitle = sagaMainTitle
            )
        return bookRepository.save(book)
    }
}