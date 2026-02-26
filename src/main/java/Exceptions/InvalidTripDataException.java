package Exceptions;

public class InvalidTripDataException extends Exception {
    public InvalidTripDataException() {
        super("Invalid Trip Data Entry.");
    }

    public InvalidTripDataException(String error) {
        super(error);
    }
}
