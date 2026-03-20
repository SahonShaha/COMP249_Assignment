// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class InvalidAccommodationDataException extends Exception{
    public InvalidAccommodationDataException() {
        super("Invalid Accommodation Data Entry.");
    }

    public InvalidAccommodationDataException(String error) {
        super(error);
    }
}
