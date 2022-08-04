package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global.GlobalResponseDto
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// TODO: Change access level
@ControllerAdvice
class GenericExceptionController {

    @ResponseBody
    @ExceptionHandler(ResponseStatusException::class)
    fun handleUnknownException(
        exception: ResponseStatusException, request: HttpServletRequest?, httpServletResponse: HttpServletResponse
    ): GlobalResponseDto<*> {
        httpServletResponse.status = exception.status.value()
        return GlobalResponseDto<Any?>(null, exception.reason!!)
    }
}