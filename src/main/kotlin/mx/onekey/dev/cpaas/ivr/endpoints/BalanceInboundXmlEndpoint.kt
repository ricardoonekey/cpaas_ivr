package mx.onekey.dev.cpaas.ivr.endpoints

import com.zang.api.domain.enums.HttpMethod
import com.zang.api.inboundxml.ZangInboundXml
import com.zang.api.inboundxml.elements.Gather
import com.zang.api.inboundxml.elements.Response
import com.zang.api.inboundxml.elements.Say
import com.zang.api.inboundxml.elements.enums.Language
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Contains all methods required to control the flow of calls.
 */
@RestController
@RequestMapping("/inbound/xml", produces = [MediaType.APPLICATION_XML_VALUE])
class BalanceInboundXmlEndpoint(private val messageSource: MessageSource) {

    @PostMapping("/gather/id")
    fun gatherAccountId() : Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val gatherLink = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java).gatherAccountNip()).toString() + "?locale=${localeLang}"
        val prompt = messageSource.getMessage("account.id.prompt", null, LocaleContextHolder.getLocale())
        val sayLang = Language.fromValue(localeLang)

        return ZangInboundXml.builder()
                .gather(
                        Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(gatherLink)
                                .say(Say.builder().setText(prompt).setLanguage(sayLang ?: Language.EN).build()
                                ).build()
                ).build()
    }

    @PostMapping("/gather/nip")
    fun gatherAccountNip() : Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val gatherLink = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java).getAccountBalance()).toString() + "?locale=${localeLang}"
        val prompt = messageSource.getMessage("account.nip.prompt", null, LocaleContextHolder.getLocale())
        val sayLang = Language.fromValue(localeLang)

        return ZangInboundXml.builder()
                .gather(
                        Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(gatherLink)
                                .say(Say.builder().setText(prompt).setLanguage(sayLang ?: Language.EN).build()
                                ).build()
                ).build()
    }

    @PostMapping("/balance")
    fun getAccountBalance(): Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val prompt = messageSource.getMessage("account.balance.statement", null, LocaleContextHolder.getLocale())
        val sayLang = Language.fromValue(localeLang)

        return ZangInboundXml.builder()
                        .say(Say.builder().setText(String.format(prompt, 0, 0)).setLanguage(sayLang ?: Language.EN).build()
                ).build()
    }

}