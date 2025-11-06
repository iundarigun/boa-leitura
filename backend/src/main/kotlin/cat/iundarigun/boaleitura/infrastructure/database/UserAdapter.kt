package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toUser
import cat.iundarigun.boaleitura.infrastructure.database.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAdapter(private val userRepository: UserRepository) : UserPort {
    @Transactional(readOnly = true)
    override fun existsByUsername(username: String): Boolean =
        userRepository.existsByUsername(username)

    @Transactional(readOnly = true)
    override fun findByUsername(username: String): User? =
        userRepository.findByUsername(username)?.toUser()

    @Transactional
    override fun save(user: User) {
        userRepository.save(user.toEntity())
    }

    @Transactional(readOnly = true)
    override fun getUserPreferences(userId: Long): UserPreferences {
        // Pending to retrieve from database
        return UserPreferences(
            languageTags = mapOf(
                "català" to "ca",
                "español" to "es",
                "português" to "pt",
                "english" to "en"
            ),
            formatTags = mapOf(
                "fisic" to "PRINTED",
                "audiobook" to "AUDIOBOOK",
                "kindle" to "EBOOK",
                "biblio" to "EBOOK",
                "unlimited" to "EBOOK",
                "sparrow" to "EBOOK",
            ),
            platformTags = mapOf(
                "fisic" to "OWN",
                "audiobook" to "AUDIBLE",
                "kindle" to "KINDLE",
                "biblio" to "EBIBLIO",
                "unlimited" to "UNLIMITED",
                "sparrow" to "OWN",
            ),
        )
    }
}