package fr.cogigroup.ingbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "account")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long solde;

    private String userFirstName;
    private String userLastName;
    private String address;
    private String phoneNumber;


    @OneToMany(mappedBy = "account")
    @JsonIgnore
    public List<Operation> operations = new ArrayList<>();

    public BankAccount() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolde() {
        return solde;
    }

    public void setSolde(Long solde) {
        this.solde = solde;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public BankAccount(Long solde, String userFirstName, String userLastName, String address, String phoneNumber) {
        this.solde = solde;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
