package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.User

interface SecurityPort {
    fun encryptPassword(password: String): String
    fun matches(loginPassword: String, savedEncryptedPassword: String): Boolean
    fun buildJwt(user: User): String
    fun validateToken(token: String): Boolean
}