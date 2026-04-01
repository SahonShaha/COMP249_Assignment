// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Service;

import Client.Client;
import Interfaces.Identifiable;
import Persistence.*;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SmartTravelService {
    //private static Client[] clients = new Client[100];
    private static List<Client> clients = new ArrayList<>();
    //private static Trip[] trips = new Trip[200];
    private static List<Trip> trips = new ArrayList<>();
    //private static Transportation[] transportations = new Transportation[50];
    private static List<Transportation> transportations = new ArrayList<>();
    //private static Accommodation[] accommodations = new Accommodation[50];
    private static List<Accommodation> accommodations = new ArrayList<>();

    public static List<Client> getClients() {
        return clients;
    }

    public static List<Trip> getTrips() {
        return trips;
    }

    public static List<Transportation> getTransportations() {
        return transportations;
    }

    public static List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public static void loadAllData(String filename) throws IOException {
        /*ClientFileManager.loadClients(clients, (filename + "clients.csv"));
        AccommodationFileManager.loadAccommodations(accommodations, (filename + "accommodations.csv"));
        TransportationFileManager.loadTransportations(transportations, (filename + "transportations.csv"));
        TripFileManager.loadTrip(trips, (filename + "trips.csv"), clients, accommodations, transportations);*/
        clients.addAll(GenericFileManager.load(filename + "clients.csv", Client.class));
        accommodations.addAll(GenericFileManager.load(filename + "accommodations.csv", Accommodation.class));
        transportations.addAll(GenericFileManager.load(filename + "transportations.csv", Transportation.class));
        trips.addAll(GenericFileManager.load(filename + "trips.csv", Trip.class));

    }

    public static void saveAllData(String filename) throws IOException {
        /*ClientFileManager.saveClients(clients, clients.size(), (filename + "clients.csv"));
        AccommodationFileManager.saveAccommodations(accommodations, accommodations.size(), (filename + "accommodations.csv"));
        TransportationFileManager.saveTransportations(transportations, transportations.size(), (filename + "transportations.csv"));
        TripFileManager.saveTrip(trips, trips.size(), (filename + "trips.csv"));*/
        GenericFileManager.save(clients, filename + "clients.csv");
        GenericFileManager.save(accommodations, filename + "accommodations.csv");
        GenericFileManager.save(transportations, filename + "transportations.csv");
        GenericFileManager.save(trips, filename + "trips.csv");
    }

    public static double calculateTripTotal(int index) {
        double basePrice = trips.get(index).getBasePrice();
        double transportationPrice = trips.get(index).getTransportation().calculateCost();
        double accommodationPrice = trips.get(index).getAccommodation().calculateCost();

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

    /*public static void showAll(Object[] objects, Object object) {
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
    }*/

    public static void showAll(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
    }

    public static void showAll(List<?> list, Class<?> type) {
        for (Object object : list) {
            if (type.isInstance(object)) {
                System.out.println(object);
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

    /*public static int findClientById(String id) {
        int clientIndex = -1;

        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                break;
            }
            if (clients[i].getId().equals(id)) {
                clientIndex = i;
                break;
            }
        }

        return clientIndex;
    }*/

    // The first parameter only takes in classes that are part of Identifiable (or the four main classes)
    public static int findById(List<? extends Identifiable> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean clientExists(List<Client> clients, String id) {
        if (findById(clients, id) == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}
