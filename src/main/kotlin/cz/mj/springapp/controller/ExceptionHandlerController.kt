package cz.mj.springapp.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

class UserNotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class UserNotCreatedException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

@ControllerAdvice
class GlobalControllerExceptionHandler {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class, UserNotCreatedException::class)
    @ResponseBody
    fun userNotFoundExceptionHandler(e: Exception): String? = e.message

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): List<String?> =
        e.allErrors.map { it.defaultMessage }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun defaultErrorHandler(e: Exception) {
        logger.info(e) { }

        if (AnnotationUtils.findAnnotation(e.javaClass, ResponseStatus::class.java) != null)
            throw e
    }
}