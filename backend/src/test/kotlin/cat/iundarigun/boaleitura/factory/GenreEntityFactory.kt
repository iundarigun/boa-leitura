package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.domain.entity.GenreEntity

object GenreEntityFactory {
    fun build(parent: GenreEntity? = null): GenreEntity =
        GenreEntity(
            name = FakerConfiguration.FAKER.lorem().characters(10, 100),
            parent = parent
        )
}