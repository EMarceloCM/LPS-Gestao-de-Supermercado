package model.exceptions;

public class CustomerException extends RuntimeException {
    public CustomerException(String msg) {
        super(msg);
    }
}