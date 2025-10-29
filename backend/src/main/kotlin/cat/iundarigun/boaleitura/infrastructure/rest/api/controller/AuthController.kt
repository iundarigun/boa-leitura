package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.auth.LoginUserUseCase
import cat.iundarigun.boaleitura.application.port.input.auth.RegisterUserUseCase
import cat.iundarigun.boaleitura.application.port.output.SecurityPort
import cat.iundarigun.boaleitura.domain.request.LoginRequest
import cat.iundarigun.boaleitura.domain.request.RegisterRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val securityPort: SecurityPort
) {

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest) {
        registerUserUseCase.execute(request)
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@Valid @RequestBody request: LoginRequest): String {
        return loginUserUseCase.execute(request)
    }

    @GetMapping("validate")
    @ResponseStatus(HttpStatus.OK)
    fun validate(@RequestParam token: String): Boolean {
        return securityPort.validateToken(token)
    }
}