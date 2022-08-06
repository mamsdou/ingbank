package fr.cogigroup.ingbank.models;

public enum OperationType {

    DEPOSIT("depot"),
    WITHDRAWAL("withdrawal");

    String operation;

    OperationType(String operation){
        this.operation=operation;
    }
}
