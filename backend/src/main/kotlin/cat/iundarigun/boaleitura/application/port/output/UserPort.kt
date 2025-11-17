package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.User

interface UserPort {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?

    fun save(user: User)
}