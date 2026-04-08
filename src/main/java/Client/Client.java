// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Client;

import Exceptions.InvalidClientDataException;
import Interfaces.CsvPersistable;
import Interfaces.Identifiable;
import Persistence.ErrorLogger;
import Service.SmartTravelService;
import Travel.Trip;

import java.io.IOException;
import java.util.List;

public class Client implements Identifiable, CsvPersistable, Comparable<Client> {
    private static int count = 1001; // Represents the amount of objects created. Will be used to create the ID
    private String clientID;
    private String firstName;
    private String lastName;
    private String email;

    // Default Constructor
    public Client(){};

    // Parametrized Constructor
    public Client(String firstName, String lastName, String email) throws InvalidClientDataException {
        this.clientID = "C" + count++;

        if (firstName.length() > 50 || lastName.length() > 50 || email.length() > 100) {
            throw new InvalidClientDataException("Entries too long. Keep First and Last Names under 50 characters and Emails under 100 characters.");
        }
        else if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidClientDataException("Email is missing the '@' or '.' characters.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Copy Constructor
    public Client(Client client) throws InvalidClientDataException {
        this.clientID = client.getId();
        if (client.getFirstName().length() > 50 || client.getLastName().length() > 50 || client.getEmail().length() > 100) {
            throw new InvalidClientDataException("Entries too long. Keep First and Last Names under 50 characters and Emails under 100 characters.");
        }
        else if (!client.getEmail().contains("@") || !client.getEmail().contains(".")) {
            throw new InvalidClientDataException("Email is missing the '@' or '.' characters.");
        }
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
    }

    public String toString() {
        return "Client ID: " + this.clientID + "\nFirst Name: " + this.firstName + "\nLast Name: " + this.lastName
                + "\nE-Mail Address: " + this.email + "\n";
    }

    public boolean equals(Client client) {
        // Ensuring the passed object is not null
        if (client == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (client.getClass() != this.getClass()) {
            return false;
        }

        if (this.firstName.equals(client.getFirstName()) && this.lastName.equals(client.getLastName())
        && this.email.equals(client.getEmail())) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toCsvRow() {
        return clientID + ";" + firstName + ";" + lastName + ";" + email;
    }

    public static Client fromCsvRow(String csvLine) throws InvalidClientDataException {
        String[] fields = csvLine.split(";");

        if (fields.length != 4) {
            throw new InvalidClientDataException("Invalid Line Format");
        }

        Client newClient = new Client(fields[1], fields[2], fields[3]);
        newClient.setClientID(fields[0]);
        return newClient;
    }

    public int compareTo(Client other) {
        return Double.compare(other.getTotalSpent(SmartTravelService.getTrips()), this.getTotalSpent(SmartTravelService.getTrips())
        );
    }

    // Helper Method for compareTo method
    public double getTotalSpent(List<Trip> trips) {
        double total = 0;

        for (Trip trip : trips) {
            if (trip.getClientOnTrip().getId().equals(this.getId())) {
                total += trip.getTotalCost();
            }
        }

        return total;
    }

    public String getId() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidClientDataException{
        if (firstName.isEmpty() || firstName.length() > 50) {
            throw new InvalidClientDataException("Invalid first name. Do not leave it blank and keep it under 50 characters.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidClientDataException {
        if (lastName.isEmpty() || lastName.length() > 50) {
            throw new InvalidClientDataException("Invalid last name. Do not leave it blank and keep it under 50 characters.");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidClientDataException {
        if (email.isEmpty() || email.length() > 100 || !email.contains("@") || !email.contains(".")) {
            throw new InvalidClientDataException("Invalid email. Do not leave it blank, keep it under 100 characters and have a \"@\" and a \".\"");
        }
        this.email = email;
    }
}
