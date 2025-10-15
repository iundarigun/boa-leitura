package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary.BookResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary.SearchResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange

interface OpenLibraryClient {
    @HttpExchange(
        method = "GET",
        value = "api/books",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchByKey(
        @RequestParam("bibkeys") key: String,
        @RequestParam("format") format: String = "json",
        @RequestParam("jscmd") jscmd: String = "data"
    ): Map<String, BookResponse>

    @HttpExchange(
        method = "GET",
        value = "search.json",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchByTitle(@RequestParam("title") title: String): SearchResponse
}