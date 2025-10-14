package cat.iundarigun.boaleitura.configuration

import net.datafaker.Faker
import org.slf4j.LoggerFactory
import java.util.Random

object FakerConfiguration {
    private val log = LoggerFactory.getLogger(FakerConfiguration::class.java)
    val FAKER = System.currentTimeMillis().let {
        log.info("seed for FAKER $it")
        Faker(Random(it))
    }

    fun Faker.randomId(): Long =
        this.number().numberBetween(100_000L, 1_000_000L)
}