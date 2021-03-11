package mx.onekey.dev.cpaas.ivr.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("callSession")
public class CallSession {

    public enum CallStatus {
        NOT_VERIFIED,
        VERIFIED
    }

    @Id
    private String callSid;

    private Account account;

    private CallStatus callStatus;

    public CallSession(String callSid) { this.callSid = callSid; }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }
}
