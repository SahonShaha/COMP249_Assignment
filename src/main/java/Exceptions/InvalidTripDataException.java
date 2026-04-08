// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class InvalidTripDataException extends Exception {
    public InvalidTripDataException() {
        super("Invalid Trip Data Entry.");
    }

    public InvalidTripDataException(String error) {
        super(error);
    }
}
