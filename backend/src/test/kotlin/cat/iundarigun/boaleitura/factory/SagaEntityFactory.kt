package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.domain.entity.SagaEntity

object SagaEntityFactory {
    fun build(): SagaEntity =
        SagaEntity(
            name = FAKER.name().fullName(),
            totalMainTitles = FAKER.number().numberBetween(2, 20),
            totalComplementaryTitles = FAKER.number().numberBetween(0, 10),
            concluded = FAKER.bool().bool()
        )
}