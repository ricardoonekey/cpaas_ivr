package mx.onekey.dev.cpaas.ivr.services

import mx.onekey.dev.cpaas.ivr.entities.Account
import mx.onekey.dev.cpaas.ivr.entities.CallSession
import mx.onekey.dev.cpaas.ivr.repositories.AccountRepository
import mx.onekey.dev.cpaas.ivr.repositories.CallSessionRepository
import org.springframework.stereotype.Service

/**
 *
 */
@Service
class SimpleCallSessionManager(private val callSessionRepository: CallSessionRepository,
                               private val accountRepository: AccountRepository) : CallSessionManager{

    override fun createSession(sessionId: String, accountId: String): Boolean {
        val account = accountRepository.findByNumber(accountId)
        return if(account.isEmpty) {
            false
        } else {
            val session = CallSession(sessionId)
            session.account = account.get()
            session.callStatus = CallSession.CallStatus.NOT_VERIFIED
            callSessionRepository.save(session)

            true
        }
    }


    override fun verifyNip(sessionId: String, accountNip: String): Boolean {
        val session = callSessionRepository.findById(sessionId)
        return if (session.isEmpty) {
            false
        } else {
            return session.get().account.nip == accountNip
        }
    }

    override fun getSessionAccount(sessionId: String): Account? {
        val session = callSessionRepository.findById(sessionId)
        return if (session.isEmpty) {
            null
        } else {
            return session.get().account
        }
    }

    override fun getSessionStatus(sessionId: String): CallSession.CallStatus? {
        val session = callSessionRepository.findById(sessionId)
        return if (session.isEmpty) {
            null
        } else {
            return session.get().callStatus
        }
    }
}