// ---------------------------------------------------------
// Assignment: 3
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
    private static List<Client> clients = new ArrayList<>();
    private static List<Trip> trips = new ArrayList<>();
    private static List<Transportation> transportations = new ArrayList<>();
    private static List<Accommodation> accommodations = new ArrayList<>();

    private static RecentList<Object> recentList = new RecentList<>();

    // Repositories
    private static Repository<Client> clientRepo = new Repository<>();
    private static Repository<Trip> tripRepo = new Repository<>();
    private static Repository<Transportation> transportationRepo = new Repository<>();
    private static Repository<Accommodation> accommodationRepo = new Repository<>();

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

    public static Repository<Client> getClientRepo() {return clientRepo;}

    public static Repository<Trip> getTripRepo() {return tripRepo;}

    public static Repository<Transportation> getTransportRepo() {return transportationRepo;}

    public static Repository<Accommodation> getAccommodationRepo() {return accommodationRepo;}

    public static RecentList<Object> getRecentList() {return recentList;}

    public static void loadAllData(String filename) throws IOException {
        clients.addAll(GenericFileManager.load(filename + "clients.csv", Client.class));
        accommodations.addAll(GenericFileManager.load(filename + "accommodations.csv", Accommodation.class));
        transportations.addAll(GenericFileManager.load(filename + "transportations.csv", Transportation.class));
        trips.addAll(GenericFileManager.load(filename + "trips.csv", Trip.class));

        // Mirroring Loading for Repository
        for (Client client : clients) {clientRepo.add(client);}
        for (Trip trip : trips) {tripRepo.add(trip);}
        for (Transportation transportation : transportations) {transportationRepo.add(transportation);}
        for (Accommodation accommodation : accommodations) {accommodationRepo.add(accommodation);}
        System.out.println("Files successfully loaded!");
    }

    // A2 Version of Loading Data
    public static void loadAllData(String filename, boolean useGenericPersistence) throws IOException {
        ClientFileManager.loadClients(clients, (filename + "clients.csv"));
        AccommodationFileManager.loadAccommodations(accommodations, (filename + "accommodations.csv"));
        TransportationFileManager.loadTransportations(transportations, (filename + "transportations.csv"));
        TripFileManager.loadTrip(trips, (filename + "trips.csv"), clients, accommodations, transportations);
    }

    public static void saveAllData(String filename) throws IOException {
        GenericFileManager.save(clients, filename + "clients.csv");
        GenericFileManager.save(accommodations, filename + "accommodations.csv");
        GenericFileManager.save(transportations, filename + "transportations.csv");
        GenericFileManager.save(trips, filename + "trips.csv");
        System.out.println("Files successfully saved!");
    }

    public static void saveAllData(String filename, boolean useGenericPersistence) throws IOException {
        ClientFileManager.saveClients(clients, clients.size(), (filename + "clients.csv"));
        AccommodationFileManager.saveAccommodations(accommodations, accommodations.size(), (filename + "accommodations.csv"));
        TransportationFileManager.saveTransportations(transportations, transportations.size(), (filename + "transportations.csv"));
        TripFileManager.saveTrip(trips, trips.size(), (filename + "trips.csv"));
    }

    public static double calculateTripTotal(int index) {
        double basePrice = trips.get(index).getBasePrice();
        double transportationPrice = trips.get(index).getTransportation().calculateCost();
        double accommodationPrice = trips.get(index).getAccommodation().calculateCost();

        return basePrice + transportationPrice + accommodationPrice;
    }

    public static int countValidObjects(Object[] objects) {
        int count = 0;
        for (Object object : objects) {
            if (object == null) {
                break;
            }
            count++;
        }

        return count;
    }

    public static void showAll(List<?> list) {
        for (Object object : list) {
            recentList.addRecent(object);
            System.out.println(object);
        }
    }

    public static void showAll(List<?> list, Class<?> type) {
        for (Object object : list) {
            if (type.isInstance(object)) {
                recentList.addRecent(object);
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
        return findById(clients, id) != -1;
    }
}
