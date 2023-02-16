package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global

import java.util.*

class ErrorDto {
    var time: String? = null
        private set
    var status: String? = null
    var message: String? = null
    var path: String? = null
    var errorType: String? = null

    constructor(status: String?, message: String?, path: String?, errorType: String?, time: String?) {
        this.time = time
        this.status = status
        this.message = message
        this.path = path
        this.errorType = errorType
    }

    constructor()

    fun setTime(time: Date) {
        this.time = time.toString()
    }

    override fun toString(): String {
        return ("ErrorDTO{"
                + "time="
                + time
                + ", status="
                + status
                + ", message='"
                + message
                + '\''
                + ", path='"
                + path
                + '\''
                + ", errorType='"
                + errorType
                + '\''
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is ErrorDto) return false
        val errorDTO = o
        return time == errorDTO.time && status === errorDTO.status && message == errorDTO.message && path == errorDTO.path && errorType == errorDTO.errorType
    }

    override fun hashCode(): Int {
        return Objects.hash(time, status, message, path, errorType)
    }
}
