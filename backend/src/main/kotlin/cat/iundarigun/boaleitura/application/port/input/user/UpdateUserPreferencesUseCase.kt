package cat.iundarigun.boaleitura.application.port.input.user

import cat.iundarigun.boaleitura.domain.model.UserPreferences

interface UpdateUserPreferencesUseCase {
    fun execute(request: UserPreferences)
}