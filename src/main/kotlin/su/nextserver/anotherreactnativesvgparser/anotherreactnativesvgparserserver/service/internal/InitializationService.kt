package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.internal

import org.springframework.stereotype.Service
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.service.main.IconService
import javax.annotation.PostConstruct


@Service
class InitializationService(private val iconService: IconService) {
    @PostConstruct
    fun initialize() {
        iconService.refreshIconBase()
    }
}