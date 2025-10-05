package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.enums.GenderType
import cat.iundarigun.boaleitura.domain.entity.AuthorEntity

object AuthorEntityFactory {
    fun build(): AuthorEntity =
        AuthorEntity(
            name = FAKER.name().fullName(),
            gender = FAKER.options().option(GenderType::class.java),
            nationality = FAKER.nation().nationality()
        )
}