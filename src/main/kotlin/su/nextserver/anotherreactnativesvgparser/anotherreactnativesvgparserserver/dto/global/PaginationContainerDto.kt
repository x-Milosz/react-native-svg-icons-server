package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global

import java.util.*

class PaginationContainerDto<T> {
    var page = 0
    var pages = 0
    var total = 0
    var contentList: List<T>? = null

    constructor(page: Int, pages: Int, total: Int, contentList: List<T>?) {
        this.page = page
        this.pages = pages
        this.total = total
        this.contentList = contentList
    }

    constructor()

    override fun toString(): String {
        return ("PaginationContainerDto{"
                + "page="
                + page
                + ", pages="
                + pages
                + ", count="
                + total
                + ", contentList="
                + contentList
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is PaginationContainerDto<*>) return false
        val that = o
        return page == that.page && pages == that.pages && total == that.total && contentList == that.contentList
    }

    override fun hashCode(): Int {
        return Objects.hash(page, pages, total, contentList)
    }
}
