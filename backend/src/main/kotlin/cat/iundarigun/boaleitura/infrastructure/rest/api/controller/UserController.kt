package cat.iundarigun.boaleitura.infrastructure.rest.api.controller

import cat.iundarigun.boaleitura.application.port.input.user.ChangePasswordUseCase
import cat.iundarigun.boaleitura.application.port.input.user.GetUserUseCase
import cat.iundarigun.boaleitura.application.port.input.user.UpdateUserPreferencesUseCase
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.request.ChangePasswordRequest
import cat.iundarigun.boaleitura.domain.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserPreferencesUseCase: UpdateUserPreferencesUseCase
) {

    @PatchMapping("password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePassword(@RequestBody request: ChangePasswordRequest) {
        changePasswordUseCase.execute(request)
    }

    @PatchMapping("preferences")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePreferences(@RequestBody request: UserPreferences) {
        updateUserPreferencesUseCase.execute(request)
    }


    @GetMapping
    fun getUser(): UserResponse =
        getUserUseCase.execute()
}