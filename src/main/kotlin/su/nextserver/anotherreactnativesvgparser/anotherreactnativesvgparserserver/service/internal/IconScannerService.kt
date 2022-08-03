package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal

import org.springframework.stereotype.Service
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors


@Service
class IconScannerService {
    private val svgsLocationPath = this.javaClass.classLoader.getResource("MaterialDesign/svg")

    fun scanSvgs(): Set<String> {
        try {
            val svgFileNames =
                Files.walk(Paths.get(svgsLocationPath.path)).map { it.fileName.toString() }.collect(Collectors.toSet())
            return svgFileNames
        } catch (e: Error) {
            println("IconScannerService::scanSvgs: ${e.toString()}")
            return emptySet()
        }
    }

}