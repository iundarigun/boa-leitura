package cat.iundarigun.boaleitura.configuration

import cat.iundarigun.boaleitura.domain.request.LoginRequest
import cat.iundarigun.boaleitura.domain.response.LoginResponse
import cat.iundarigun.boaleitura.domain.security.UserToken
import cat.iundarigun.boaleitura.factory.DataFactory
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestContainerBaseConfiguration {
    @LocalServerPort
    lateinit var port: Integer

    @Autowired
    lateinit var dataFactory: DataFactory

    var jwtToken: String? = null

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

        jwtToken = RestTemplate()
            .postForEntity(
                "http://localhost:$port/auth/login",
                LoginRequest("admin", "admin"),
                LoginResponse::class.java
            ).body?.accessToken
    }

    fun clearData() {
        executeInContext { dataFactory.clean() }
    }

    fun <T> executeInContext(func: () -> T): T {
        addUserToContext()
        val result = func.invoke()
        removeUserFromContext()
        return result
    }

    private fun addUserToContext() {
        val userTokenMock = Mockito.mock(UserToken::class.java)
        Mockito.`when`(userTokenMock.userId).thenReturn(DataFactory.userId)
        SecurityContextHolder.getContext().authentication = userTokenMock
    }

    private fun removeUserFromContext() {
        SecurityContextHolder.getContext().authentication = null
    }
}