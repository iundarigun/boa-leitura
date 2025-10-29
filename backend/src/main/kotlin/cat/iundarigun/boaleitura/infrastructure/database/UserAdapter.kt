package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.model.User
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
}