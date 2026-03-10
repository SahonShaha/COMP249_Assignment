package Service;

import Client.Client;
import Persistence.AccommodationFileManager;
import Persistence.ClientFileManager;
import Persistence.TransportationFileManager;
import Persistence.TripFileManager;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.IOException;

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

    // todo WHAT SHOULD THE FILENAME BE
    public static void loadAllData(String filename) throws IOException {
        ClientFileManager.loadClients(clients, filename);
        AccommodationFileManager.loadAccommodations(accommodations, filename);
        TransportationFileManager.loadTransportations(transportations, filename);
        TripFileManager.loadTrip(trips, filename, clients, accommodations, transportations);
    }

    // todo WHAT SHOULD THE FILENAME BE
    public static void saveAllData(String filename) throws IOException {
        ClientFileManager.saveClients(clients, countValidObjects(clients), filename);
        AccommodationFileManager.saveAccommodations(accommodations, countValidObjects(accommodations), filename);
        TransportationFileManager.saveTransportations(transportations, countValidObjects(transportations), filename);
        TripFileManager.saveTrip(trips, countValidObjects(trips), filename);
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
}
