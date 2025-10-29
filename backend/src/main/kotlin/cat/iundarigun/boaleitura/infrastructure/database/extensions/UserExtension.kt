package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.infrastructure.database.entity.UserEntity

fun User.toEntity(): UserEntity =
    UserEntity(
        username = this.username,
        encryptedPassword = this.encryptedPassword
    )

fun UserEntity.toUser(): User =
    User(
        id = this.id,
        username = this.username,
        encryptedPassword = this.encryptedPassword
    )