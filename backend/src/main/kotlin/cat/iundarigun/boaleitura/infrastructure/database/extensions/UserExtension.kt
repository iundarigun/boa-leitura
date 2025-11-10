package cat.iundarigun.boaleitura.infrastructure.database.extensions

import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.domain.model.UserPreferences
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
        encryptedPassword = this.encryptedPassword,
        userPreferences = this.toUserPreferences()
    )

fun UserEntity.toUserPreferences(): UserPreferences =
    UserPreferences(
        languageTags = this.languageTags,
        formatTags = this.formatTags,
        platformTags = this.platformTags,
    )

fun UserEntity.merge(user: User): UserEntity {
    this.username = user.username
    this.encryptedPassword = user.encryptedPassword
    this.languageTags = user.userPreferences.languageTags
    this.formatTags = user.userPreferences.formatTags
    this.platformTags = user.userPreferences.platformTags
    return this
}