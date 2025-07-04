package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.GenderType
import cat.iundarigun.boaleitura.domain.request.AuthorRequest

object AuthorRequestFactory {
    fun build(): AuthorRequest =
        AuthorRequest(
            name = FAKER.name().fullName(),
            gender = FAKER.options().option(GenderType::class.java),
            nationality = FAKER.nation().nationality()
        )
}