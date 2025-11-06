package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.user.ChangePasswordUseCase
import cat.iundarigun.boaleitura.domain.request.ChangePasswordRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(private val changePasswordUseCase: ChangePasswordUseCase) {

    @PatchMapping("password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePassword(@RequestBody request: ChangePasswordRequest) {
        changePasswordUseCase.execute(request)
    }
}