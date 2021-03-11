package mx.onekey.dev.cpaas.ivr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class Account implements Serializable {

    public static final Long serialVersionUID = 1L;

    @GeneratedValue
    @Id
    private Long id;

    @NotEmpty
    @Pattern(regexp = "[0-9]{4}")
    private String number;

    @NotEmpty
    @Pattern(regexp = "[0-9]{4}")
    private String nip;

    private Float balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
