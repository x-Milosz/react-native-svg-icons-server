package name.milosznowaczyk.reactnativesvgicons.server.service.internal

import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Service


@Service
class IconScannerService {
    private val svgsLocationPath = this.javaClass.classLoader.getResource("MaterialDesign/svg")

    fun scanSvgs(): Set<String> {
        return try {

            val scannedPackage = "MaterialDesign/svg/*"
            val scanner = PathMatchingResourcePatternResolver()
            val resources = scanner.getResources(scannedPackage)
                .filter{ checkIfResourcesIsSvg(it)}
                .map { extractFileName(it) }.toSet()

            resources
        } catch (e: Error) {
            println("IconScannerService::scanSvgs: ${e}")
            emptySet()
        }
    }

    private fun checkIfResourcesIsSvg(res: Resource): Boolean {
        val tmp = res.url.path.split("/");
        if (tmp.size < 3) {
            return false
        }
        return tmp[tmp.size - 2] == "svg"
    }

    private fun extractFileName(res: Resource): String {
        val tmp = res.url.path.split("/");
        return tmp[tmp.size - 1];
    }
}
