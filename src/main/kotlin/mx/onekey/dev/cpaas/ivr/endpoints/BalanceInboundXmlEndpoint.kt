package mx.onekey.dev.cpaas.ivr.endpoints

import com.zang.api.domain.enums.HttpMethod
import com.zang.api.inboundxml.ZangInboundXml
import com.zang.api.inboundxml.elements.Gather
import com.zang.api.inboundxml.elements.Response
import com.zang.api.inboundxml.elements.Say
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.ws.rs.Path

/**
 * Contains all methods required to control the flow of calls.
 */
@RestController
@RequestMapping("/inbound/xml", produces = [MediaType.APPLICATION_XML_VALUE])
class BalanceInboundXmlEndpoint(private val messageSource: MessageSource) {

    @PostMapping("/welcome")
    fun welcome(): Response {
        val gatherLink = linkTo(methodOn(BalanceInboundXmlEndpoint::class.java).gatherAccountId()).toString()
        val prompt = messageSource.getMessage("account.id.prompt", null, Locale("es")) //TODO LocaleUrlFilter
        return ZangInboundXml.builder()
            .gather(
                Gather.builder().setMethod(HttpMethod.POST).setNumDigits(4).setAction(gatherLink)
                    .say(Say.builder().setText(prompt).build()
                ).build()
            ).build()
    }

    @PostMapping("/gather/id")
    fun gatherAccountId() : Response {
        return ZangInboundXml.builder().build() //TODO IMPLEMENT
    }

    @PostMapping("/gather/nip")
    fun gatherAccountNip() : Response {
        return ZangInboundXml.builder().build() //TODO IMPLEMENT
    }

    @PostMapping("/balance")
    fun getAccountBalance(): Response {
        return ZangInboundXml.builder().build() //TODO IMPLEMENT
    }

}