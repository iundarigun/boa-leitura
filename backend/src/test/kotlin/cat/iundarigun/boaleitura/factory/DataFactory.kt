package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import org.springframework.stereotype.Repository

@Repository
class DataFactory(
    private val readingRepository: ReadingRepository,
    private val sagaRepository: SagaRepository,
    private val genreRepository: GenreRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {
    fun clean() {
        readingRepository.deleteAll()
        bookRepository.deleteAll()
        authorRepository.deleteAll()
        sagaRepository.deleteAll()
        genreRepository.deleteAll()
    }
}