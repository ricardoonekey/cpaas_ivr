package mx.onekey.dev.cpaas.ivr.services

import mx.onekey.dev.cpaas.ivr.entities.Account
import mx.onekey.dev.cpaas.ivr.entities.CallSession

interface CallSessionManager {
    fun createSession(sessionId: String, accountId: String): Boolean
    fun verifyNip(sessionId: String, accountNip: String): Boolean
    fun getSessionAccount(sessionId: String): Account?
    fun getSessionStatus(sessionId: String): CallSession.CallStatus?
}