package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.domain.request.GenreRequest

object GenreRequestFactory {
    fun build(parentGenreId: Long? = null): GenreRequest =
        GenreRequest(
            name = FakerConfiguration.FAKER.book().genre(),
            parentGenreId = parentGenreId
        )
}