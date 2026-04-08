// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Exceptions;

public class InvalidTransportDataException extends Exception{
    public InvalidTransportDataException() {
        super("Invalid Transport Data Entry.");
    }

    public InvalidTransportDataException(String error) {
        super(error);
    }
}
