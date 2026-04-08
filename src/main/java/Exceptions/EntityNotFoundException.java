// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super("Entity not found.");
    }

    public EntityNotFoundException(String error) {
        super(error);
    }
}
