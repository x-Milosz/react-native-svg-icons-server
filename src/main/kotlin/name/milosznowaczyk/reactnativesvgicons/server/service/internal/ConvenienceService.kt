package name.milosznowaczyk.reactnativesvgicons.server.service.internal

import org.springframework.stereotype.Service

@Service
class ConvenienceService(
    val exceptionOperatorService: ExceptionOperatorService,
    val responseService: ResponseService
)
