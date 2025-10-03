package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.GenderType
import cat.iundarigun.boaleitura.domain.entity.Author

object AuthorFactory {
    fun build(): Author =
        Author(
            name = FAKER.name().fullName(),
            gender = FAKER.options().option(GenderType::class.java),
            nationality = FAKER.nation().nationality()
        )
}