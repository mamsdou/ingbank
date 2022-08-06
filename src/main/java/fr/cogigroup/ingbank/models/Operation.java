package fr.cogigroup.ingbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "operations")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant date;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    private Long montant ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Operation() {
    }

    public Operation(Instant date, OperationType type, Long montant, BankAccount account) {
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.account = account;
    }
}
