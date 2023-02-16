package name.milosznowaczyk.reactnativesvgicons.server.controller

import name.milosznowaczyk.reactnativesvgicons.server.dto.global.GlobalResponseDto
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException
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
