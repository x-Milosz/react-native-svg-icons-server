package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main.IconService

@RestController
@RequestMapping(value = ["/api/icons"])
class IconController(
    private val iconService: IconService,
) {
    @CrossOrigin(origins = arrayOf("http://localhost:3000/"))
    @GetMapping
    fun getIcons(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) pageSize: Int?,
        @RequestParam(required = false) search: String?
    ): ResponseEntity<*>? {
        return iconService.getIcons(page, pageSize, search)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:3000/"))
    @GetMapping("/{id}")
    fun getIconSvg(@PathVariable(name = "id") iconId: Long): ResponseEntity<*>? {
        return iconService.getIconSvg(iconId)?.get()
    }
}
