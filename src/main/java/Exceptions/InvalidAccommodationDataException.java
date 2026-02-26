package Exceptions;

public class InvalidAccommodationDataException extends Exception{
    public InvalidAccommodationDataException() {
        super("Invalid Accommodation Data Entry.");
    }

    public InvalidAccommodationDataException(String error) {
        super(error);
    }
}
