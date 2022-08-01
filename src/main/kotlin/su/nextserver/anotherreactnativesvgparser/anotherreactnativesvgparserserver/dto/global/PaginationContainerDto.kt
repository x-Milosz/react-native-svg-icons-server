package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.global

import java.util.*

class PaginationContainerDto<T> {
    var page = 0
    var pages = 0
    var quantity = 0
    var contentList: List<T>? = null

    constructor(page: Int, pages: Int, quantity: Int, contentList: List<T>?) {
        this.page = page
        this.pages = pages
        this.quantity = quantity
        this.contentList = contentList
    }

    constructor() {}

    override fun toString(): String {
        return ("PaginationContainerDto{"
                + "page="
                + page
                + ", pages="
                + pages
                + ", count="
                + quantity
                + ", contentList="
                + contentList
                + '}')
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is PaginationContainerDto<*>) return false
        val that = o
        return page == that.page && pages == that.pages && quantity == that.quantity && contentList == that.contentList
    }

    override fun hashCode(): Int {
        return Objects.hash(page, pages, quantity, contentList)
    }
}