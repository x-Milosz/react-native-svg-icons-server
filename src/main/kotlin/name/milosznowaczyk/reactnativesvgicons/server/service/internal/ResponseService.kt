package name.milosznowaczyk.reactnativesvgicons.server.service.internal

import name.milosznowaczyk.reactnativesvgicons.server.dto.global.GlobalResponseDto
import name.milosznowaczyk.reactnativesvgicons.server.dto.global.GlobalResponseWithStatusCodeDto
import name.milosznowaczyk.reactnativesvgicons.server.dto.global.PaginationContainerDto
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
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
        total: Int,
        code: String?
    ): ResponseEntity<*>? {
        return wrap(PaginationContainerDto(page, pages, total, contentList), code)
    }

    fun <T> wrapWithPaginationContainer(
        contentList: List<T>,
        page: Int,
        pages: Int,
        total: Int,
        code: String?,
        statusCode: Int
    ): ResponseEntity<*>? {
        return wrap(PaginationContainerDto(page, pages, total, contentList), code, statusCode)
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
