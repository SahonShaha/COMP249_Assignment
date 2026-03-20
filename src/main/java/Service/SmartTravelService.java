package Service;

import Client.Client;
import Persistence.*;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.IOException;

public class SmartTravelService {
    private static Client[] clients = new Client[100];
    private static Trip[] trips = new Trip[200];
    private static Transportation[] transportations = new Transportation[50];
    private static Accommodation[] accommodations = new Accommodation[50];

    public static Client[] getClients() {
        return clients;
    }

    public static Trip[] getTrips() {
        return trips;
    }

    public static Transportation[] getTransportations() {
        return transportations;
    }

    public static Accommodation[] getAccommodations() {
        return accommodations;
    }

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
    }

    public static int findClientById(String id) {
        int clientIndex = -1;

        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                break;
            }
            if (clients[i].getClientID().equals(id)) {
                clientIndex = i;
                break;
            }
        }

        return clientIndex;
    }

    public static boolean clientExists(String id) {
        if (findClientById(id) == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
