package name.milosznowaczyk.reactnativesvgicons.server.dto.global

import java.util.*

open class GlobalResponseDto<T>(val data: T, val message: String) {

    override fun toString(): String {
        return "GlobalResponseDto{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as GlobalResponseDto<*>
        return data == that.data && message == that.message
    }

    override fun hashCode(): Int {
        return Objects.hash(data, message)
    }
}
