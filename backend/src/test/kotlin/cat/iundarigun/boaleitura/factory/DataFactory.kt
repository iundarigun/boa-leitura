package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.infrastructure.database.entity.UserEntity
import cat.iundarigun.boaleitura.infrastructure.database.repository.AuthorRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class DataFactory(
    private val readingRepository: ReadingRepository,
    private val sagaRepository: SagaRepository,
    private val genreRepository: GenreRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    passwordEncoder: PasswordEncoder,
    userRepository: UserRepository
) {
    init {
        userId =
            userRepository.save(UserEntity(username = "admin", encryptedPassword = passwordEncoder.encode("admin"))).id
    }

    fun clean() {
        readingRepository.deleteAll()
        bookRepository.deleteAll()
        authorRepository.deleteAll()
        sagaRepository.deleteAll()
        genreRepository.deleteAll()
    }

    companion object {
        var userId: Long = 0L
    }
}