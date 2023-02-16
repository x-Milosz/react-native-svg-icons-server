package name.milosznowaczyk.reactnativesvgicons.server.service.internal

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ExceptionOperatorService(private val messageSource: MessageSource) {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionOperatorService::class.java)

    @Throws(ResponseStatusException::class)
    fun intercept(e: Error) {
        logger.error(e.toString())
        if (e is ResponseStatusException) {
            throw e
        }
        throw ResponseStatusException(500, e.localizedMessage, e)
    }

    @Throws(ResponseStatusException::class)
    fun throwException(code: String?, httpStatus: HttpStatus?) {
        throw ResponseStatusException(
            httpStatus!!, messageSource.getMessage(code!!, null, Locale.getDefault())
        )
    }

    /**
     * Default HttpStatus is 400
     *
     * @param code
     */
    @Throws(ResponseStatusException::class)
    fun throwException(code: String?) {
        throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, messageSource.getMessage(code!!, null, Locale.getDefault())
        )
    }

    @Throws(ResponseStatusException::class)
    fun <T> throwExceptionIfOptionalIsEmpty(tOptional: Optional<T>, code: String?): T {
        if (tOptional.isEmpty) {
            throwException(code)
        }
        return tOptional.get()
    }

    @Throws(ResponseStatusException::class)
    fun <T> throwExceptionIfOptionalIsEmpty(
        tOptional: Optional<T>, code: String?, httpStatus: HttpStatus?
    ): T {
        if (tOptional.isEmpty) {
            throwException(code, httpStatus)
        }
        return tOptional.get()
    }

    /**
     *
     * @param t parameter that tOptional value will be assigned if tOptional is present
     * @param tOptional
     * @param <T>
    </T> */
    fun <T> assignValueToParameterIfOptionalIsPresent(t: T, tOptional: Optional<T>) {
        var t = t
        if (tOptional.isPresent) {
            t = tOptional.get()
        }
    }
}
