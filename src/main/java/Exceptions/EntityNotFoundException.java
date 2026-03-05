package Exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super("Entity not fount.");
    }

    public EntityNotFoundException(String error) {
        super(error);
    }
}
