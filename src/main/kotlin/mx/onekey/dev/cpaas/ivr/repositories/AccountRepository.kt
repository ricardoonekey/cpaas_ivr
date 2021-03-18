package mx.onekey.dev.cpaas.ivr.repositories

import mx.onekey.dev.cpaas.ivr.entities.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, Long>, PagingAndSortingRepository<Account, Long> {
    fun findByNumber(accountNumber: String): Optional<Account>
}