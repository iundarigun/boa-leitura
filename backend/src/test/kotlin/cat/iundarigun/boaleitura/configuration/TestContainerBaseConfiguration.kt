package cat.iundarigun.boaleitura.configuration

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestContainerBaseConfiguration {
    @LocalServerPort
    lateinit var port: Integer

    companion object {
        val POSTGRESQL_CONTAINER = PostgreSQLContainer("postgres")
            .withDatabaseName("boaleituratest")
            .withUsername("usertest")
            .withPassword("passwordtest")

        init {
            Startables.deepStart(POSTGRESQL_CONTAINER).join()
        }

        @JvmStatic
        @DynamicPropertySource
        private fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl)
            registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername)
            registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword)
        }
    }

    @BeforeEach
    fun before() {
        RestAssured.port = this.port.toInt()
    }
}