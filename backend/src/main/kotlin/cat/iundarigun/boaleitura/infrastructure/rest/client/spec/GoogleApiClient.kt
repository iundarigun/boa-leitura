package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.googleapi.GoogleApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange

interface GoogleApiClient {
    @HttpExchange(
        method = "GET",
        value = "books/v1/volumes",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchBy(@RequestParam("q") text: String): GoogleApiResponse

    fun searchByIsbn(isbn: String): GoogleApiResponse {
        return searchBy("isbn:$isbn")
    }

    fun searchByTitle(title: String): GoogleApiResponse {
        return searchBy("intitle:$title")
    }
}