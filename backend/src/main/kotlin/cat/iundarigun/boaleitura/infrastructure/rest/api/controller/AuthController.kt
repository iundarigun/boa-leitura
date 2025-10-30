package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.auth.LoginUserUseCase
import cat.iundarigun.boaleitura.application.port.input.auth.RegisterUserUseCase
import cat.iundarigun.boaleitura.domain.request.LoginRequest
import cat.iundarigun.boaleitura.domain.request.RegisterRequest
import cat.iundarigun.boaleitura.domain.response.LoginResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
) {

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest) {
        registerUserUseCase.execute(request)
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@Valid @RequestBody request: LoginRequest): LoginResponse {
        return loginUserUseCase.execute(request)
    }
}