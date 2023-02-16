package name.milosznowaczyk.reactnativesvgicons.server.service.internal

import name.milosznowaczyk.reactnativesvgicons.server.service.main.IconService
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


@Service
class InitializationService(private val iconService: IconService) {
    @PostConstruct
    fun initialize() {
        iconService.refreshIconBase()
    }
}
