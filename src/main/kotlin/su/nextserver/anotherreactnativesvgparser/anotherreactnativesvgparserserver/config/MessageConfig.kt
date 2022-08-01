package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class MessageConfig {
    @Bean
    fun messageSource(): MessageSource? {
        val reloadableResourceBundleMessageSource = ReloadableResourceBundleMessageSource()
        reloadableResourceBundleMessageSource.setBasenames(
            "classpath:messages/validation/validation",
            "classpath:messages/response/response"
        )
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8")
        return reloadableResourceBundleMessageSource
    }
}