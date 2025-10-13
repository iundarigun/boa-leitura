package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.GoogleApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.HttpExchange

interface GoogleApiClient {
    @HttpExchange(
        method = "GET",
        value = "books/v1/volumes?q=isbn:{isbn}",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchByIsbn(@PathVariable isbn: String): GoogleApiResponse
}