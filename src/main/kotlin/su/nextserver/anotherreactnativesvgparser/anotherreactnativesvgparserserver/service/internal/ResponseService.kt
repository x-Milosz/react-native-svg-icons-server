package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal

import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global.GlobalResponseDto
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global.GlobalResponseWithStatusCodeDto
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global.PaginationContainerDto
import java.util.*

@Service
class ResponseService(private val messageSource: MessageSource) {
    fun <T> wrap(data: T, code: String?): ResponseEntity<*>? {
        return responseEntityWrap(
            GlobalResponseWithStatusCodeDto(
                data,
                messageSource.getMessage(code!!, null, Locale.getDefault())
            )
        )
    }

    fun <T> wrap(data: T, code: String?, statusCode: Int): ResponseEntity<*>? {
        return responseEntityWrap(
            GlobalResponseWithStatusCodeDto(
                data,
                messageSource.getMessage(code!!, null, Locale.getDefault()),
                statusCode
            )
        )
    }

    fun <T> wrapWithPaginationContainer(
        contentList: List<T>,
        page: Int,
        pages: Int,
        quantity: Int,
        code: String?
    ): ResponseEntity<*>? {
        return wrap(PaginationContainerDto(page, pages, quantity, contentList), code)
    }

    fun <T> wrapWithPaginationContainer(
        contentList: List<T>,
        page: Int,
        pages: Int,
        quantity: Int,
        code: String?,
        statusCode: Int
    ): ResponseEntity<*>? {
        return wrap(PaginationContainerDto(page, pages, quantity, contentList), code, statusCode)
    }


    private fun <T> responseEntityWrap(
        globalResponseWithStatusCodeDto: GlobalResponseWithStatusCodeDto<T>
    ): ResponseEntity<*>? {
        return ResponseEntity.status(globalResponseWithStatusCodeDto.statusCode)
            .body<Any>(
                GlobalResponseDto<T>(
                    globalResponseWithStatusCodeDto.data,
                    globalResponseWithStatusCodeDto.message
                )
            )
    }
}