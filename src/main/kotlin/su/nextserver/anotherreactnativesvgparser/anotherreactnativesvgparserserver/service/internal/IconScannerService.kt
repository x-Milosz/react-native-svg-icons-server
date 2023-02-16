package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal

import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.io.path.name


@Service
class IconScannerService {
    private val svgsLocationPath = this.javaClass.classLoader.getResource("MaterialDesign/svg")

    fun scanSvgs(): Set<String> {
        try {
            val svgFileNames =
                Files.walk(Paths.get(svgsLocationPath.path)).map { it.fileName.name }.filter { it != "svg" }
                    .collect(Collectors.toSet())
            return svgFileNames
        } catch (e: Error) {
            println("IconScannerService::scanSvgs: ${e}")
            return emptySet()
        }
    }

}
