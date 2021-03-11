package mx.onekey.dev.cpaas.ivr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ResourceBundleMessageSource


@SpringBootApplication
class Application {

    @Bean
    fun messageSource(): MessageSource? {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("i18n/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}