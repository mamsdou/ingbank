package fr.cogigroup.ingbank.exceptions;

public class NoSuchAccountException extends Throwable {

    String message;

    public NoSuchAccountException(String message) {
        this.message = message;
    }
}
