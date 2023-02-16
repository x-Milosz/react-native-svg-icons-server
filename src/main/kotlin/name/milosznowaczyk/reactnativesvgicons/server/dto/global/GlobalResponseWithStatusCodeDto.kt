package name.milosznowaczyk.reactnativesvgicons.server.dto.global

import java.util.*

class GlobalResponseWithStatusCodeDto<T> : GlobalResponseDto<T> {
    val statusCode: Int

    constructor(data: T, message: String) : super(data, message) {
        statusCode = 200
    }

    constructor(data: T, message: String, statusCode: Int) : super(data, message) {
        this.statusCode = statusCode
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is GlobalResponseWithStatusCodeDto<*>) return false
        if (!super.equals(o)) return false
        return statusCode == o.statusCode
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), statusCode)
    }

    override fun toString(): String {
        return "GlobalResponseWithStatusCodeDto{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}'
    }
}
