package cat.iundarigun.boaleitura.infrastructure.rest.client.spec

import cat.iundarigun.boaleitura.infrastructure.rest.client.dto.googleapi.GoogleApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange

interface GoogleApiClient {
    @Retryable(
        retryFor = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(
            delayExpression = "512",
            multiplierExpression = "2",
            maxDelayExpression = "16000"
        ),
        recover = "defaultResult"
    )
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

    fun defaultResult(): GoogleApiResponse {
        logger.warn("recover result")
        return GoogleApiResponse()
    }

    companion object {
        val logger = LoggerFactory.getLogger(GoogleApiClient::class.java)
    }
}