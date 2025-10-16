package cat.iundarigun.boaleitura.factory

import cat.iundarigun.boaleitura.configuration.FakerConfiguration.FAKER
import cat.iundarigun.boaleitura.infrastructure.database.entity.SagaEntity

object SagaEntityFactory {
    fun build(name: String = FAKER.name().fullName()): SagaEntity =
        SagaEntity(
            name = name,
            totalMainTitles = FAKER.number().numberBetween(2, 20),
            totalComplementaryTitles = FAKER.number().numberBetween(0, 10),
            concluded = FAKER.bool().bool()
        )
}