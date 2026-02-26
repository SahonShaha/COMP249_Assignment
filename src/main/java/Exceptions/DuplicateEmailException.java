package Exceptions;

public class DuplicateEmailException extends Exception {
    // Exception for when the email is already taken
    public DuplicateEmailException() {
        super("The following email address already exists.");
    }
}
