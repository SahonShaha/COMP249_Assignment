// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class DuplicateEmailException extends RuntimeException {
    // Exception for when the email is already taken
    public DuplicateEmailException() {
        super("The following email address already exists.");
    }
}
