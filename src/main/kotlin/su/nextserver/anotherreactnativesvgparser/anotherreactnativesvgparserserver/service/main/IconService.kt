package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main

import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
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
    private final val SEARCH_CONDITIONS_EXAMPLE_MATCHER =
        ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withIgnorePaths("id", "creation_date", "last_modification_date")

    private final val dummyDate = Date()

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
            var page = 1
            if (wantedPage != null) {
                page = wantedPage
            }

            var pageSize = 50
            if (wantedPageSize != null) {
                pageSize = wantedPageSize
            }

//            var exampleIcon: Example<Icon> = Example.of(Icon(1, "", dummyDate, dummyDate))
//            if (wantedSearch !== null) {
//                exampleIcon = Example.of(Icon(1, wantedSearch, dummyDate, dummyDate), SEARCH_CONDITIONS_EXAMPLE_MATCHER)
//            }

            val pageRequest = PageRequest.of(page, pageSize)

//            val responsePage = iconRepository.findAll(pageRequest)
            val responsePage = iconRepository.findAll(pageRequest)


            return convenienceService.responseService.wrapWithPaginationContainer(
                responsePage.toList(),
                page,
                responsePage.totalPages,
                responsePage.size,
                "getIconsSuccessful"
            )
        } catch (e: Error) {
            convenienceService.exceptionOperatorService.intercept(e)
            return null
        }
    }
}