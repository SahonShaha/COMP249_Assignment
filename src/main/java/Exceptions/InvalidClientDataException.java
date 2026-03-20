// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class InvalidClientDataException extends Exception {
    // Exception for blank first name, last name or emails
    public InvalidClientDataException() {
        super("Invalid Client Data Entry.");
    }

    // When throwing an exception, we will pass a message as a parameter that specifies the error
    public InvalidClientDataException(String errorMessage) {
        super(errorMessage);
    }
}
