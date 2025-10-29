package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:Size(min = 8, max = 50)
    val username: String,
    @field:Size(min = 8, max = 20)
    val password: String,
    val repeatPassword: String
) {
    @AssertTrue(message = "Password must be the same")
    fun isPasswordCorrect(): Boolean {
        return password == repeatPassword
    }
}
