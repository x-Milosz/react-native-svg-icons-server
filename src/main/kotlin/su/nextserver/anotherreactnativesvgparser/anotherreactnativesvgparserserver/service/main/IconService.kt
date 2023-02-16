package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.icon.IconListItemDto
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.dto.icon.IconSvgDto
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.entity.Icon
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.repository.IconRepository
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.ConvenienceService
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.IconScannerService
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.svgfilereader.ReadSvgFile
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.svgfilereader.SvgFileName
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.svgfilereader.SvgFileReaderService
import java.util.*
import java.util.concurrent.Future
import javax.transaction.Transactional


@Service
class IconService(
    private val iconRepository: IconRepository,
    private val iconScannerService: IconScannerService,
    private val convenienceService: ConvenienceService,
    private val svgFileReaderService: SvgFileReaderService
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
        iconRepository.saveAll(svgNames.map { Icon(0, it.split(".")[0], dummyDate, dummyDate) })
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

            val svgFiles =
                svgFileReaderService.readMultipleSvgFiles(responsePage.map { SvgFileName(it.id, it.name) }.toList())
            val properContent = matchQueriedIconListWithFiles(responsePage.toList(), svgFiles)

            return convenienceService.responseService.wrapWithPaginationContainer(
                properContent,
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

    private fun matchQueriedIconListWithFiles(icons: List<Icon>, svgFiles: List<ReadSvgFile>): List<IconListItemDto> {
        val iconListItemDtoList: ArrayList<IconListItemDto> = ArrayList()
        for (icon in icons) {
            for (svgFile in svgFiles) {
                if (icon.id == svgFile.id) {
                    iconListItemDtoList.add(IconListItemDto(icon.id, icon.name, svgFile.svg))
                    break
                }
            }
        }
        return iconListItemDtoList
    }

    private fun searchIcons(pageRequest: PageRequest, wantedSearch: String?): Page<Icon> {
        if (wantedSearch != null) {
            return iconRepository.findByNameContainsAllIgnoreCaseOrderByNameAsc(wantedSearch, pageRequest)
        }

        return iconRepository.findByOrderByNameAsc(pageRequest)
    }

    fun getIconSvg(iconId: Long): Future<ResponseEntity<*>>? {
        try {
            val requestedIconOptional = iconRepository.findById(iconId)
            if (requestedIconOptional.isEmpty) {
                convenienceService.exceptionOperatorService.throwException("getIconSvgNotFound")
                return null
            }

            val svg = svgFileReaderService.readSingleSvgFile(requestedIconOptional.get().name)

            return AsyncResult(
                convenienceService.responseService.wrap(
                    IconSvgDto(
                        requestedIconOptional.get().name,
                        svg
                    ), "getIconSvgSuccessful"
                )
            )
        } catch (e: Error) {
            convenienceService.exceptionOperatorService.intercept(e)
            return null
        }
    }

}
