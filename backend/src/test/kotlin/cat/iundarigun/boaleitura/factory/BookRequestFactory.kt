package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.domain.model.BookOriginalEditionModel
import cat.iundarigun.boaleitura.domain.request.BookRequest
import cat.iundarigun.boaleitura.domain.request.BookSagaRequest
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import org.springframework.stereotype.Service

@Service
class BookRequestFactory(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val sagaRepository: SagaRepository,
) {
    fun build(
        authorId: Long = authorRepository.save(AuthorEntityFactory.build()).id,
        genreId: Long = genreRepository.save(GenreEntityFactory.build()).id,
        sagaId: Long? = sagaRepository.save(SagaEntityFactory.build()).id,
        title: String = FakerConfiguration.FAKER.book().title(),
        language: String = FakerConfiguration.FAKER.languageCode().iso639(),
        numberOfPages: Int = FakerConfiguration.FAKER.number().numberBetween(25, 2500),
        publisherYear: Int = FakerConfiguration.FAKER.number().numberBetween(1900, 2025),
        originalEdition: BookOriginalEditionModel? = BookOriginalEditionModel(
            title = FakerConfiguration.FAKER.book().title(),
            language = FakerConfiguration.FAKER.languageCode().iso639()
        ),
        isbn: String = FakerConfiguration.FAKER.lorem().characters(10, 15),
        urlImage: String? = FakerConfiguration.FAKER.internet().url(),
        urlImageSmall: String? = FakerConfiguration.FAKER.internet().url()
    ): BookRequest =
        BookRequest(
            title = title,
            authorId = authorId,
            genreId = genreId,
            language = language,
            numberOfPages = numberOfPages,
            publisherYear = publisherYear,
            originalEdition = originalEdition,
            saga = sagaId?.let {
                BookSagaRequest(
                    id = it,
                    order = FakerConfiguration.FAKER.number().randomDouble(1, 1, 10),
                    mainTitle = FakerConfiguration.FAKER.bool().bool()
                )
            },
            isbn = isbn,
            urlImage = urlImage,
            urlImageSmall = urlImageSmall
        )
}