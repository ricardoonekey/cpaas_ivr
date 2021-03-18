package mx.onekey.dev.cpaas.ivr.endpoints

import mx.onekey.dev.cpaas.ivr.entities.Account
import mx.onekey.dev.cpaas.ivr.repositories.AccountRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/account")
class AccountRestEndpoint(private val accountRepository: AccountRepository) {

    /**
     * You probably don't want to make this information public in a production environment
     */
    @GetMapping("/balance", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAccountBalances(@RequestParam(defaultValue = "0") page: Int,
                           @RequestParam(defaultValue = "10") size: Int): Collection<Account> {
        return accountRepository.findAll(PageRequest.of(page, size)).content
    }
}
