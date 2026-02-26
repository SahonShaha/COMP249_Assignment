package Exceptions;

public class InvalidTransportDataException extends Exception{
    public InvalidTransportDataException() {
        super("Invalid Transport Data Entry.");
    }

    public InvalidTransportDataException(String error) {
        super(error);
    }
}
