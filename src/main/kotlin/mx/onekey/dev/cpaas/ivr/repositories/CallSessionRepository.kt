package mx.onekey.dev.cpaas.ivr.repositories

import mx.onekey.dev.cpaas.ivr.entities.CallSession
import org.springframework.data.repository.CrudRepository

/**
 * Stores the state of each call. Uses Avaya's Call SID as the key.
 */
interface CallSessionRepository : CrudRepository<CallSession, String> {
}