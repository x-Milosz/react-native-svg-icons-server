package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.icon.IconListItemDto
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.entity.Icon
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.repository.IconRepository
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.ConvenienceService
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.IconScannerService
import java.util.*
import javax.transaction.Transactional


@Service
class IconService(
    private val iconRepository: IconRepository,
    private val iconScannerService: IconScannerService,
    private val convenienceService: ConvenienceService
) {
    /**
     * Refreshes database icons when database or resource icons number has changed
     */
    @Transactional
    fun refreshIconBase() {
        val svgNames = iconScannerService.scanSvgs()

        val numberOfSvgsInDb = iconRepository.count()
        if (svgNames.size.toLong() == numberOfSvgsInDb) {
            return
        }

        iconRepository.deleteAll()
        val dummyDate = Date()
        iconRepository.saveAll(svgNames.map { Icon(0, it, dummyDate, dummyDate) })
    }


    fun getIcons(wantedPage: Int?, wantedPageSize: Int?, wantedSearch: String?): ResponseEntity<*>? {
        try {
            var page = 0
            if (wantedPage != null) {
                page = wantedPage - 1
            }

            var pageSize = 50
            if (wantedPageSize != null) {
                pageSize = wantedPageSize
            }

            val pageRequest = PageRequest.of(page, pageSize)

            val responsePage = searchIcons(pageRequest, wantedSearch)


            return convenienceService.responseService.wrapWithPaginationContainer(
                responsePage.toList().map { IconListItemDto(it.id, it.name) },
                page + 1,
                responsePage.totalPages,
                responsePage.totalElements.toInt(),
                "getIconsSuccessful"
            )
        } catch (e: Error) {
            convenienceService.exceptionOperatorService.intercept(e)
            return null
        }
    }

    private fun searchIcons(pageRequest: PageRequest, wantedSearch: String?): Page<Icon> {
        if (wantedSearch != null) {
            return iconRepository.findByNameContainsAllIgnoreCase(wantedSearch, pageRequest)
        }

        return iconRepository.findAll(pageRequest)
    }
}