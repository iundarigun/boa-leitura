package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest

object ToBeReadRequestFactory {
    fun build(bookId: Long, position: Long? = null): ToBeReadRequest =
        ToBeReadRequest(
            bookId = bookId,
            position = position,
            bought = FAKER.bool().bool(),
            platforms = FAKER.lorem().words(FAKER.number().numberBetween(2, 5)),
            tags = FAKER.lorem().words(FAKER.number().numberBetween(2, 5)),
            notes = FAKER.lorem().characters(FAKER.number().numberBetween(10, 1000))
        )
}