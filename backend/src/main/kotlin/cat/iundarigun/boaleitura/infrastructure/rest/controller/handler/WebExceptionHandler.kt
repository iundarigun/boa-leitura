package cat.iundarigun.boaleitura.infrastructure.rest.controller.handler

import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.exception.BoaLeituraBusinessException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class WebExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BoaLeituraBusinessException::class)
    fun handleBoaLeituraBusinessException(exception: BoaLeituraBusinessException): ResponseEntity<ErrorResponse> {
        logger.warn("handleBoaLeituraBusinessException, message={}", exception.message)
        return ResponseEntity
            .status(exception.httpStatus)
            .body(ErrorResponse(exception.httpStatus.value(), exception.message))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException
    ): ResponseEntity<ErrorResponse> {
        logger.warn("handleHttpMessageNotReadableException, message={}", exception.message)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {
        logger.warn("handlerMethodArgumentNotValidException, message={}", exception.message)

        val message = if (exception.bindingResult.allErrors.isNotEmpty()) {
            exception.bindingResult.allErrors.joinToString {
                "${(it as FieldError).field}: ${it.defaultMessage}"
            }
        } else {
            exception.message
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), message))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handlerMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorResponse> {
        logger.warn("handlerMethodArgumentTypeMismatchException, message={}", exception.message)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), "${exception.propertyName}: ${exception.message}"))
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.warn("handlerException, message={}", exception.message, exception)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error: ${exception.message}"))
    }
}