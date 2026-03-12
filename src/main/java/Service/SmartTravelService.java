package Service;

import Client.Client;
import Exceptions.DuplicateEmailException;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidClientDataException;
import Exceptions.InvalidTripDataException;
import Persistence.*;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


// TODO ADD GENERIC METHODS HERE
public class SmartTravelService {
    private static Client[] clients = new Client[100];
    private static Trip[] trips = new Trip[200];
    private static Transportation[] transportations = new Transportation[50];
    private static Accommodation[] accommodations = new Accommodation[50];

    public Client[] getClients() {
        return clients;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public Transportation[] getTransportations() {
        return transportations;
    }

    public Accommodation[] getAccommodations() {
        return accommodations;
    }

    /*public static void add(Object[] objects, Object object) {
        if (objects[objects.length - 1] != null) {
            System.out.println("Storage Full.");
        }
        else {
            for (int i = 0; i < objects.length; i++) { // We iterate until we reach a null element
                if (objects[i] == null) {
                    objects[i] = object;
                    break;
                }
            }
        }
    }*/

    public static void loadAllData(String filename) throws IOException {
        ClientFileManager.loadClients(clients, (filename + "clients.csv"));
        AccommodationFileManager.loadAccommodations(accommodations, (filename + "accommodations.csv"));
        TransportationFileManager.loadTransportations(transportations, (filename + "transportations.csv"));
        TripFileManager.loadTrip(trips, (filename + "trips.csv"), clients, accommodations, transportations);
    }

    public static void saveAllData(String filename) throws IOException {
        ClientFileManager.saveClients(clients, countValidObjects(clients), (filename + "clients.csv"));
        AccommodationFileManager.saveAccommodations(accommodations, countValidObjects(accommodations), (filename + "accommodations.csv"));
        TransportationFileManager.saveTransportations(transportations, countValidObjects(transportations), (filename + "transportations.csv"));
        TripFileManager.saveTrip(trips, countValidObjects(trips), (filename + "trips.csv"));
    }

    public static double calculateTripTotal(int index) {
        double basePrice = trips[index].getBasePrice();
        double transportationPrice = trips[index].getTransportation().calculateCost();
        double accommodationPrice = trips[index].getAccommodation().calculateCost();

        return basePrice + transportationPrice + accommodationPrice;
    }

    public static int countValidObjects(Object[] objects) {
        int count = 0;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                break;
            }
            count++;
        }

        return count;
    }

    public static void showAll(Object[] objects, Object object) {
        // Prints all non-null objects
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) { // Once we reach the first null in the array, it means that the rest will be null too
                break;
            }
            else {
                if (objects[i].getClass() == object.getClass()) {
                    //System.out.println("\nEntry " + (i+1));
                    System.out.println(objects[i]);
                }
            }
        }
    }

    public static void add(Object[] objects, Object object) {
        if (objects[objects.length - 1] != null) { // The 19 is hard coded due to the size of the class arrays
            System.out.println("Storage Full.");
        }
        else {
            for (int i = 0; i < objects.length; i++) { // We iterate until we reach a null element
                if (objects[i] == null) {
                    objects[i] = object;
                    break;
                }
            }
        }
    }

    public static void editClient(Scanner scanner, Client[] clients, int clientIndex) {
        boolean running = true;
        while (running) {
            System.out.println("""
                        Which section do you want to edit?
                        1 - First Name
                        2 - Last Name
                        3 - Email
                        4 - Finished Editing
                        """);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter a new first name: ");
                    try {
                        clients[clientIndex].setFirstName(scanner.next());
                        System.out.println("First Name Updated to: " + clients[clientIndex].getFirstName());
                    }
                    catch (InvalidClientDataException invalidClientDataException) {
                        System.out.println(invalidClientDataException.getMessage());
                        try {
                            ErrorLogger.log(invalidClientDataException);
                        }
                        catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter a new last name: ");
                    try {
                        clients[clientIndex].setLastName(scanner.next());
                        System.out.println("Last Name Updated to: " + clients[clientIndex].getLastName());
                    }
                    catch (InvalidClientDataException invalidClientDataException) {
                        System.out.println(invalidClientDataException.getMessage());
                        try {
                            ErrorLogger.log(invalidClientDataException);
                        }
                        catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Enter a new email: ");
                    try {
                        clients[clientIndex].setEmail(scanner.next());
                        System.out.println("Email Updated to: " + clients[clientIndex].getEmail());
                    }
                    catch (InvalidClientDataException invalidClientDataException) {
                        System.out.println(invalidClientDataException.getMessage());
                        try {
                            ErrorLogger.log(invalidClientDataException);
                        }
                        catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
                case 4 -> running = false;
                default -> System.out.println("Invalid Input. Try Again");
            }
        }
    }
}
