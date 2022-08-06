package fr.cogigroup.ingbank.models.dto;

import fr.cogigroup.ingbank.models.Operation;

import java.util.ArrayList;
import java.util.List;

public class BankAccountDto {

    private Long solde;

    public List<Operation> latestOperations = new ArrayList<>();

    public Long getSolde() {
        return solde;
    }

    public void setSolde(Long solde) {
        this.solde = solde;
    }

    public List<Operation> getLatestOperations() {
        return latestOperations;
    }

    public void setLatestOperations(List<Operation> latestOperations) {
        this.latestOperations = latestOperations;
    }
}
