package mx.onekey.dev.cpaas.ivr.repositories

import mx.onekey.dev.cpaas.ivr.entities.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByNumber(accountNumber: String): Optional<Account>
}