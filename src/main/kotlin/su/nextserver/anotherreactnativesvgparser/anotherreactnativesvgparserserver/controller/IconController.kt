package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main.IconService

@RestController
@RequestMapping(value = ["/api/icons"])
class IconController(
    private val iconService: IconService,
) {
    @GetMapping
    fun getIcons(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) pageSize: Int?,
        @RequestParam(required = false) search: String?
    ): ResponseEntity<*>? {
        return iconService.getIcons(page, pageSize, search);
    }
}