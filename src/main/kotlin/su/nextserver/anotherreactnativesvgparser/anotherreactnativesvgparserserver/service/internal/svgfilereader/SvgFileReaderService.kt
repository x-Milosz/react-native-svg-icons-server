package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.svgfilereader

import org.springframework.stereotype.Service
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal.ConvenienceService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.Semaphore

@Service
class SvgFileReaderService(
    private val convenienceService: ConvenienceService
) {

    /**
     *  @param svgFileName without .svg suffix
     */
    fun readSingleSvgFile(svgFileName: String): String {
        val requestedIconInputStream =
            javaClass.classLoader.getResourceAsStream("MaterialDesign/svg/${svgFileName}.svg")
        if (requestedIconInputStream == null) {
            convenienceService.exceptionOperatorService.throwException("getIconSvgCouldNotRead")
            return ""
        }

        val inputStreamReader = InputStreamReader(requestedIconInputStream, StandardCharsets.UTF_8)
        val bufferedReader = BufferedReader(inputStreamReader)
        val svg = bufferedReader.readText()
        bufferedReader.close()
        inputStreamReader.close()
        requestedIconInputStream.close()

        return svg
    }

    fun readMultipleSvgFiles(svgFileNames: List<SvgFileName>): List<ReadSvgFile> {

        val svgFileNamesSemaphore = Semaphore(1)

        val restOfSizeSemaphore = Semaphore(1)
        var restOfSizes = svgFileNames.size - 1

        val readFilesSemaphore = Semaphore(1)
        val readFiles: ArrayList<ReadSvgFile> = ArrayList()

        val threads: ArrayList<Thread> = ArrayList()
        for (i in 0..5) {
            threads.add(Thread {
                while (true) {
                    restOfSizeSemaphore.acquireUninterruptibly()
                    val currentIndex = restOfSizes
                    restOfSizes--
                    restOfSizeSemaphore.release()
                    if (currentIndex < 0) {
                        break
                    }

                    svgFileNamesSemaphore.acquireUninterruptibly()
                    val currentSvgFileName = svgFileNames[currentIndex]
                    svgFileNamesSemaphore.release()

                    val response: String = readSingleSvgFile(currentSvgFileName.name)

                    readFilesSemaphore.acquireUninterruptibly()
                    readFiles.add(ReadSvgFile(currentSvgFileName.id, response))
                    readFilesSemaphore.release()
                }
            })
        }

        for (thread in threads) {
            thread.start()
        }

        for (thread in threads) {
            thread.join()
        }

        return readFiles
    }
}
