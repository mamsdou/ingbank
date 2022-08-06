package fr.cogigroup.ingbank.models.dto;

public class OperationTreatment {
    private long amount ;

    public OperationTreatment() {
    }

    public OperationTreatment(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
