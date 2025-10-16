package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.enums.GenderType
import cat.iundarigun.boaleitura.infrastructure.database.entity.AuthorEntity

object AuthorEntityFactory {
    fun build(name: String = FAKER.name().fullName()): AuthorEntity =
        AuthorEntity(
            name = name,
            gender = FAKER.options().option(GenderType::class.java),
            nationality = FAKER.nation().nationality()
        )
}