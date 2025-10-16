package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.infrastructure.database.entity.GenreEntity

object GenreEntityFactory {
    fun build(
        parent: GenreEntity? = null,
        name: String = FakerConfiguration.FAKER.lorem().characters(10, 100)
    ): GenreEntity =
        GenreEntity(
            name = name,
            parent = parent
        )
}