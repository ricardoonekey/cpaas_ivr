package mx.onekey.dev.cpaas.ivr.endpoints

import com.zang.api.domain.enums.HttpMethod
import com.zang.api.inboundxml.ZangInboundXml
import com.zang.api.inboundxml.elements.Gather
import com.zang.api.inboundxml.elements.Response
import com.zang.api.inboundxml.elements.Say
import com.zang.api.inboundxml.elements.enums.Language
import mx.onekey.dev.cpaas.ivr.services.CallSessionManager
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Contains all methods required to control the flow of calls.
 */
@RestController
@RequestMapping("/inbound/xml", produces = [MediaType.APPLICATION_XML_VALUE])
class BalanceInboundXmlEndpoint(private val messageSource: MessageSource,
                                private val callSessionManager: CallSessionManager) {

    /**
     * Greets the user and prompts him for his account number.
     */
    @PostMapping("/welcome")
    fun welcome(@RequestParam request: Map<String, String>) : Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val gatherLink = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java)
            .gatherAccountId(mapOf("locale" to localeLang))).toString()
        val prompt = messageSource.getMessage("account.id.prompt", null, LocaleContextHolder.getLocale())
        val sayLang = Language.fromValue(localeLang)

        return ZangInboundXml.builder()
                .gather(
                        Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(gatherLink)
                                .say(Say.builder().setText(prompt).setLanguage(sayLang ?: Language.EN).build()
                                ).build()
                ).build()
    }

    /**
     * Captures our customer's account ID and prompts him for his account NIP.
     */
    @PostMapping("/gather/id")
    fun gatherAccountId(@RequestParam request: Map<String, String>) : Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val localeParam = mapOf("locale" to localeLang)
        val sayLang = Language.fromValue(localeLang)

        val link: String
        val prompt: String
        if(callSessionManager.createSession(request["CallSid"] ?: "", request["Digits"] ?: "")) {
            link = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java)
                .gatherNipAndStateBalance(mapOf("locale" to localeLang))).toString()
            prompt = messageSource.getMessage("account.nip.prompt", null, LocaleContextHolder.getLocale())
        }
        else {
            link = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java).gatherAccountId(localeParam)).toString()
            prompt = messageSource.getMessage("error.account.not.found", null, LocaleContextHolder.getLocale())
        }

        return ZangInboundXml.builder()
                .gather(
                        Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(link)
                                .say(Say.builder().setText(prompt).setLanguage(sayLang ?: Language.EN).build()
                                ).build()
                ).build()
    }


    /**
     * Gathers our customer's NIP and if correct states his balance, otherwise he is prompted again for his NIP.
     */
    @PostMapping("/gather/nip")
    fun gatherNipAndStateBalance(@RequestParam request: Map<String, String>): Response {
        val localeLang = LocaleContextHolder.getLocale().language
        val localeParam = mapOf("locale" to localeLang)
        val sayLang = Language.fromValue(localeLang)

        if(callSessionManager.verifyNip(request["CallSid"] ?: "", request["Digits"] ?: "")) {
            val account = callSessionManager.getSessionAccount(request["CallSid"] ?: "")
            val prompt = if( account != null) {
                val balance = account.balance.toString().split(".")

                String.format(
                    messageSource.getMessage("account.balance.statement", null, LocaleContextHolder.getLocale()),
                    balance[0], balance[1]
                )
            } else {
                messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale())
            }

            return ZangInboundXml.builder()
                .say(Say.builder().setText(prompt).setLanguage(sayLang ?: Language.EN).build()
                ).build()
        }
        else {
            val link = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java)
                .gatherNipAndStateBalance(localeParam)).toString()
            val prompt = messageSource.getMessage("error.nip.incorrect", null, LocaleContextHolder.getLocale())

            return ZangInboundXml.builder()
                .gather(Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(link)
                    .say(Say.builder().setLanguage(sayLang).setText(prompt).build())
                    .build())
                .build()
        }
    }

}