package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary.BookResponse
import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.openlibrary.SearchResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange

interface OpenLibraryClient {
    @Retryable(
        retryFor = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(
            delayExpression = "512",
            multiplierExpression = "2",
            maxDelayExpression = "16000"
        ),
        recover = "defaultResultSearchByKey"
    )
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

    @Retryable(
        retryFor = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(
            delayExpression = "512",
            multiplierExpression = "2",
            maxDelayExpression = "16000"
        ),
        recover = "defaultResultSearchByTitle"
    )
    @HttpExchange(
        method = "GET",
        value = "search.json",
        accept = [MediaType.APPLICATION_JSON_VALUE],
        contentType = MediaType.APPLICATION_JSON_VALUE
    )
    fun searchByTitle(@RequestParam("title") title: String): SearchResponse

    fun defaultResultSearchByKey(): Map<String, BookResponse> {
        logger.warn("defaultResultSearchByKey")
        return mapOf()
    }

    fun defaultResultSearchByTitle(): SearchResponse {
        logger.warn("defaultResultSearchByTitle")
        return SearchResponse()
    }

    companion object {
        val logger = LoggerFactory.getLogger(GoogleApiClient::class.java)
    }
}