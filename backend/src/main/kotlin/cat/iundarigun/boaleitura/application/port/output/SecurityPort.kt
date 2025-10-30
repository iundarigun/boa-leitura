package cat.iundarigun.boaleitura.application.port.output

import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.domain.response.LoginResponse

interface SecurityPort {
    fun encryptPassword(password: String): String
    fun matches(loginPassword: String, savedEncryptedPassword: String): Boolean
    fun generateLoginToken(user: User): LoginResponse
}