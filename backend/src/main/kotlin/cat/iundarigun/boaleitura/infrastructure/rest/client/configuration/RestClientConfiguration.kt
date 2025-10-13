package cat.iundarigun.boaleitura.infrastructure.rest.client.configuration

import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.GoogleApiClient
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.OpenLibraryClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class RestClientConfiguration {

    @Bean
    fun openLibraryClient(
        @Value("\${restClient.openLibrary.baseUrl}") baseUrl: String,
        restClientBuilder: RestClient.Builder
    ): OpenLibraryClient {
        val restClientAdapter = RestClientAdapter.create(
            generateRestClient(
                baseUrl,
                restClientBuilder
            )
        )
        return HttpServiceProxyFactory.builderFor(restClientAdapter)
            .build()
            .createClient(OpenLibraryClient::class.java)
    }

    @Bean
    fun googleApiClient(
        @Value("\${restClient.googleApi.baseUrl}") baseUrl: String,
        restClientBuilder: RestClient.Builder
    ): GoogleApiClient {
        val restClientAdapter = RestClientAdapter.create(
            generateRestClient(
                baseUrl,
                restClientBuilder
            )
        )
        return HttpServiceProxyFactory.builderFor(restClientAdapter)
            .build()
            .createClient(GoogleApiClient::class.java)
    }

    private fun generateRestClient(
        baseUrl: String,
        restClientBuilder: RestClient.Builder
    ): RestClient {
        return restClientBuilder.baseUrl(baseUrl)
            .requestFactory(JdkClientHttpRequestFactory())
            .build()
    }
}