package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.OpenLibraryResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.HttpExchange

interface OpenLibraryClient {
    @HttpExchange(
        method = "GET",
        value = "books?bibkeys=ISBN:{isbn}&format=json&jscmd=data",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchByIsbn(@PathVariable isbn: String): Map<String, OpenLibraryResponse>
}