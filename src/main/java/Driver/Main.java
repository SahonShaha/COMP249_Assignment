// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Driver;

import Exceptions.*;
import Travel.*;
import Client.*;
import Visualization.TripChartGenerator;

import java.io.IOException;
import java.util.Scanner;
// TODO PROPAGATE ENTITY NOT FOUND EXCEPTION
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMenuRunning = true;

        // Creating storage for the arrays
        // TODO UPDATED ARRAY QUANTITY
        Client[] clients = new Client[20]; // 100 MAX
        Trip[] trips = new Trip[20]; // 200 MAX
        Transportation[] transportations = new Transportation[20]; // 50 MAX
        Accommodation[] accommodations = new Accommodation[20]; // 50 MAX

        System.out.println("Welcome to SmartTravel!");

        while (isMenuRunning) {
            boolean userMenu = true;

            System.out.println("""
                Would you like to use the application or run the testing scenario?
                1 - Run Application
                2 - Execute Testing Scenario
                3 - Generate Diagrams
                4 - Exit
                """);
            int initialChoice = scanner.nextInt();

            switch (initialChoice) {
                case 1 -> {
                    while (userMenu) {
                        System.out.println("""
                        1. Client Management
                        2. Trip Management
                        3. Transportation Management
                        4. Accommodation Management
                        5. Additional Operations
                        6. Exit
                        """);
                        int choice = scanner.nextInt();

                        switch (choice) {
                            case 1 -> clientManagement(scanner, clients);
                            case 2 -> tripManagement(scanner, trips, clients, transportations, accommodations);
                            case 3 -> transportationManagement(scanner, transportations);
                            case 4 -> accommodationManagement(scanner, accommodations);
                            case 5 -> additionalOperations(scanner, trips, transportations, accommodations);
                            case 6 -> userMenu = false;
                            default -> System.out.println("Invalid Option.");
                        }
                    }
                }

                case 2 -> testingScenario(clients, transportations, accommodations, trips);
                case 3 -> {
                    try {
                        TripChartGenerator.generateCostBarChart(trips, countValidObjects(trips));
                        TripChartGenerator.generateDurationLineChart(trips, countValidObjects(trips));
                        TripChartGenerator.generateDestinationPieChart(trips, countValidObjects(trips));
                    }
                    catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                }
                case 4 -> {
                    System.out.println("Closing Application...Goodbye!");
                    isMenuRunning = false;
                }
            }
        }
    }

    public static void clientManagement(Scanner scanner, Client[] clients) {
        System.out.println("""
                1. Add a client
                2. Edit a client
                3. Delete a client
                4. List all clients
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> { // ADDING CLIENT
                    System.out.println("Enter you First Name: ");
                    String firstName = scanner.next();
                    System.out.println("Enter your Last Name: ");
                    String lastName = scanner.next();
                    System.out.println("Enter your Email: ");
                    String email = scanner.next();

                try {
                    // First we check if there is no duplicate emails.
                    for (int i = 0; i < clients.length; i++) {
                        if (clients[i] == null) {
                            break;
                        }
                        if (clients[i].getEmail().equals(email)) {
                            throw new DuplicateEmailException();
                        }
                    }
                    Client newClient = new Client(firstName, lastName, email);
                    add(clients, newClient);
                }
                catch (InvalidClientDataException invalidClientDataException) {
                    System.out.println("Error: " + invalidClientDataException);
                }
                catch (DuplicateEmailException duplicateEmailException) {
                    System.out.println(duplicateEmailException);
                }
            }
            case 2 -> { // Editing Clients
                if (clients[0] == null) {
                    System.out.println("No Clients created. Please create some first.");
                }
                else {
                    showAll(clients, new Client());

                    System.out.println("Choose the ID of a client you want to edit: ");
                    String clientID = scanner.next();
                    int clientEditIndex = - 1; // If this stays -1, that means a client of that ID doesn't exist

                    for (int i = 0; i < clients.length; i++) {
                        if (clients[i] == null) { // Once it hits null, that means all available elements have been printed
                            break;
                        }

                        if (clients[i].getClientID().equals(clientID)) {
                            clientEditIndex = i;
                        }
                    }

                    // Validating the user choice
                    try {
                        if (clientEditIndex == -1) {
                            throw new EntityNotFoundException("Client does not exist.");
                        } else {
                            editClient(scanner, clients, clientEditIndex);
                        }
                    }
                    catch (EntityNotFoundException entityNotFoundException) {
                        System.out.println(entityNotFoundException);
                    }
                }
            }
            case 3 -> { // Deleting Clients
                showAll(clients, new Client());

                System.out.println("Choose the ID of a client you want to edit: ");
                String clientID = scanner.next();
                int clientEditIndex = - 1; // If this stays -1, that means a client of that ID doesn't exist

                for (int i = 0; i < clients.length; i++) {
                    if (clients[i] == null) { // Once it hits null, that means all available elements have been printed
                        break;
                    }

                    if (clients[i].getClientID().equals(clientID)) {
                        clientEditIndex = i;
                    }
                }

                // Validating the user choice
                try {
                    if (clientEditIndex == -1) {
                        throw new EntityNotFoundException("Client does not exist.");
                    } else {
                        System.out.println("Updated list of Clients post-deletion:");
                        showAll(clients, new Client());
                    }
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException);
                }
            }
            case 4 -> showAll(clients, new Client());
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void tripManagement(Scanner scanner, Trip[] trips, Client[] clients, Transportation[] transportations, Accommodation[] accommodations) {
        System.out.println("""
                1. Create a trip
                2. Edit trip information
                3. Cancel a trip
                4. List all trips
                5. List all trips for a specific client
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> { // Creating a Trip
                System.out.println("Enter the destination: ");
                String destination = scanner.next();
                System.out.println("Enter the duration of the trip (in days): ");
                int duration = scanner.nextInt();
                System.out.println("Enter the base price of the trip: ");
                int basePrice = scanner.nextInt();

                System.out.println("Enter the ID one of the following clients that will be going on this trip: ");
                showAll(clients, new Client());
                String clientId = scanner.next();

                System.out.println("Enter the ID of one of the following transportations the client will take: ");
                for (int i = 0; i < transportations.length; i++) {
                    if (transportations[i] == null) {
                        break;
                    }

                    System.out.println(transportations[i]);
                }
                String transportationId = scanner.next();


                System.out.println("Enter the ID of one of the following accommodations the client will take: ");
                for (int i = 0; i < accommodations.length; i++) {
                    if (accommodations[i] == null) {
                        break;
                    }

                    System.out.println(accommodations[i]);
                }
                String accommodationId = scanner.next();

                int clientIndex = -1;
                int transportIndex = -1;
                int accommodationIndex = -1;

                // LOCATING INDEX OF SELECTED CLIENT, TRANSPORTATION AND ACCOMMODATION
                for (int i = 0; i < clients.length; i++) {
                    if (clients[i] == null) {
                        break;
                    }
                    if (clients[i].getClientID().equals(clientId)) {
                        clientIndex = i;
                        break;
                    }
                }

                for (int i = 0; i < transportations.length; i++) {
                    if (transportations[i] == null) {
                        break;
                    }
                    if (transportations[i].getTransportationID().equals(transportationId)) {
                        transportIndex = i;
                        break;
                    }
                }

                for (int i = 0; i < accommodations.length; i++) {
                    if (accommodations[i] == null) {
                        break;
                    }
                    if (accommodations[i].getAccommodationID().equals(accommodationId)) {
                        accommodationIndex = i;
                        break;
                    }
                }

                try {
                    // Ensuring the indices have been updated
                    if (clientIndex == -1) {
                        throw new EntityNotFoundException("Client does not exist.");
                    }
                    if (transportIndex == -1) {
                        throw new EntityNotFoundException("Transportation does not exist.");
                    }
                    if (accommodationIndex == -1) {
                        throw new EntityNotFoundException("Accommodation does not exist.");
                    }

                    Trip trip = new Trip(destination, duration, basePrice, clients[clientIndex], transportations[transportIndex], accommodations[accommodationIndex]);
                    add(trips, trip);
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException);
                }
                catch (InvalidTripDataException invalidTripDataException) {
                    System.out.println(invalidTripDataException);
                }
            }
            case 2 -> { // Editing Trip
                if (trips[0] == null) {
                    System.out.println("No Trips created. Please create some first.");
                }
                else {
                    showAll(trips, new Trip());

                    System.out.println("Enter the ID of the trip you want to edit: ");
                    String id = scanner.next();
                    try {
                        editTrip(scanner, trips, id, clients, transportations, accommodations);
                    }
                    catch (EntityNotFoundException entityNotFoundException) {
                        System.out.println(entityNotFoundException);
                    }
                }
            }
            case 3 -> { // Deleting Trip
                showAll(trips, new Trip());
                System.out.println("Choose the ID of the trip you want to delete: ");
                String id = scanner.next();
                try {
                    deleteTrip(trips, id);
                    System.out.println("Updated list of Trips post-deletion:");
                    showAll(trips, new Trip());
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException);
                }

            }
            case 4 -> showAll(trips, new Trip());
            case 5 -> { // Showing Trips based on a client ID
                showAll(clients, new Client());
                System.out.println("Enter the ID of the client you want to see the trips of: ");
                String clientID = scanner.next();
                int clientIndex = -1;

                for (int i = 0; i < clients.length; i++) {
                    if (clients[i] == null) {
                        break;
                    }

                    if (clients[i] != null && clients[i].getClientID().equals(clientID)) {
                        clientIndex = i;
                    }
                }

                try {
                    if (clientIndex == -1) {
                        throw new EntityNotFoundException("Client does not exist.");
                    }
                    else {
                        for (int i = 0; i < trips.length; i++) {
                            if (trips[i] == null) {
                                break; // If we reach a null object, that means we've gone through every existing trip
                            }

                            // We check if the client of trip at i is the same as the client the user chose
                            if (trips[i].getClientOnTrip().equals(clients[clientIndex])) {
                                System.out.println(trips[i]);
                            }
                        }
                    }
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException);
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    // TODO EntityNotFound Propagation
    public static void transportationManagement(Scanner scanner, Transportation[] transportations) {
        System.out.println("""
                1. Add a transportation option
                2. Remove a transportation option
                3. List transportation options by type
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> { // Adding transportation
                System.out.println("What type of transportation?");
                System.out.println("""
                        1. Bus
                        2. Flight
                        3. Train
                        """);
                int transportationChoice = scanner.nextInt();
                // Getting the inputs for Transportation Class Attributes that is commonly shared across all 3 types
                System.out.println("Enter the Company Name: ");
                String companyName = scanner.next();
                System.out.println("Enter the City of Departure: ");
                String departureCity = scanner.next();
                System.out.println("Enter the City of Arrival: ");
                String arrivalCity = scanner.next();


                switch (transportationChoice) {
                    case 1 -> { // Adding a bus
                        System.out.println("Enter the name of the Bus Company: ");
                        String busCompany = scanner.next();
                        System.out.println("Enter the amount of stops during the transit: ");
                        int stops = scanner.nextInt();
                        System.out.println("Enter the base fare: ");
                        int baseFare = scanner.nextInt();
                        try {
                            Bus newBus = new Bus(companyName, departureCity, arrivalCity, busCompany, stops, baseFare);
                            add(transportations, newBus);
                        }
                        catch (InvalidTransportDataException invalidTransportDataException) {
                            System.out.println(invalidTransportDataException);
                        }
                    }

                    case 2 -> { // Adding a flight
                        System.out.println("Enter the Airline Name: ");
                        String airlineName = scanner.next();
                        System.out.println("Enter the maximum weight of luggage: ");
                        int weight = scanner.nextInt();
                        System.out.println("Enter the ticket price: ");
                        int ticketPrice = scanner.nextInt();

                        try {
                            Flight newFlight = new Flight(companyName, departureCity, arrivalCity, airlineName, weight, ticketPrice);
                            add(transportations, newFlight);
                        }
                        catch (InvalidTransportDataException invalidTransportDataException) {
                            System.out.println(invalidTransportDataException);
                        }
                    }

                     case 3 -> { // Adding a train
                         System.out.println("Enter the type of train: ");
                         String trainType = scanner.next();
                         System.out.println("Enter class of seats (Basic, Cabin or Deluxe): ");
                         String seatClass = scanner.next();
                         System.out.println("Enter the fare: ");
                         int fare = scanner.nextInt();

                         Train newTrain = new Train(companyName, departureCity, arrivalCity, trainType, seatClass, fare);
                         add(transportations, newTrain);
                     }
                    default -> System.out.println("Invalid Input");
                }
            }

            case 2 -> { // Deleting a transportation
                System.out.println("What type of transportation?");
                System.out.println("""
                        1. Bus
                        2. Flight
                        3. Train
                        """);
                int transportationChoice = scanner.nextInt();

                switch (transportationChoice) {
                    case 1 -> {
                        showAll(transportations, new Bus());
                    }
                    case 2 -> {
                        showAll(transportations, new Flight());
                    }

                    case 3 -> {
                        showAll(transportations, new Train());
                    }
                    default -> System.out.println("Invalid Input.");
                }

                System.out.println("Choose the ID of a Transportation you want to delete: ");
                String id = scanner.next();
                deleteTransportation(transportations, id);
            }

            case 3 -> { // Showing Transportations
                System.out.println("Which type of transportation do you want to list?");
                System.out.println("""
                        1. Buses
                        2. Flights
                        3. Trains
                        4. All
                        """);
                int listViewChoice = scanner.nextInt();

                switch (listViewChoice) {
                    case 1 -> showAll(transportations, new Bus());
                    case 2 -> showAll(transportations, new Flight());
                    case 3 -> showAll(transportations, new Train());
                    case 4 -> {
                        System.out.println("BUSES");
                        showAll(transportations, new Bus());
                        System.out.println("FLIGHTS");
                        showAll(transportations, new Flight());
                        System.out.println("TRAINS");
                        showAll(transportations, new Train());
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void accommodationManagement(Scanner scanner, Accommodation[] accommodations) {
        System.out.println("""
                1. Add an accommodation
                2. Remove an accommodation
                3. List accommodations by type
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> { // Adding an accommodation
                System.out.println("What type of accommodation?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        """);
                int accommodationChoice = scanner.nextInt();

                System.out.println("Enter the Name: ");
                String name = scanner.next();
                System.out.println("Enter the Location: ");
                String location = scanner.next();
                System.out.println("Enter the price per night: ");
                int pricePerNight = scanner.nextInt();

                switch (accommodationChoice) {
                    case 1 -> { // Adding a hostel
                        System.out.println("Enter the number of beds per room: ");
                        int beds = scanner.nextInt();

                        try {
                            Hostel newHostel = new Hostel(name, location, pricePerNight, beds);
                            add(accommodations, newHostel);
                        }
                        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                            System.out.println(invalidAccommodationDataException);
                        }
                    }
                    case 2 -> { // Adding a hotel
                        System.out.println("Enter the number of stars: ");
                        int stars = scanner.nextInt();

                        try {
                            Hotel newHotel = new Hotel(name, location, pricePerNight, stars);
                            add(accommodations, newHotel);
                        }
                        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                            System.out.println(invalidAccommodationDataException);
                        }
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            case 2 -> { // Deleting an accommodation
                System.out.println("What type of accommodation?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        """);
                int accommodationChoice = scanner.nextInt();

                switch (accommodationChoice) {
                    case 1 -> {
                        showAll(accommodations, new Hostel());
                    }
                    case 2 -> {
                        showAll(accommodations, new Hotel());
                    }
                    default -> System.out.println("Invalid Input.");
                }

                System.out.println("Choose the ID of an Accommodation you want to delete: ");
                String id = scanner.next();
                deleteAccommodation(accommodations, id);
            }
            case 3 -> { // Showing accommodations
                System.out.println("Which type of transportation do you want to list?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        3. All
                        """);
                int listViewChoice = scanner.nextInt();

                switch (listViewChoice) {
                    case 1 -> showAll(accommodations, new Hostel());
                    case 2 -> showAll(accommodations, new Hotel());
                    case 3 -> {
                        System.out.println("Hostels");
                        showAll(accommodations, new Hostel());
                        System.out.println("Hotels");
                        showAll(accommodations, new Hotel());
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void additionalOperations(Scanner scanner, Trip[] trips, Transportation[] transportations, Accommodation[] accommodations) {
        System.out.println("""
                1. Display the Most Expensive Trip
                2. Display the Total Cost of a Trip
                3. Create a Deep Copy of the Transportation Array
                4. Create a Deep Copy of the Accommodation Array
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println(mostExpensiveTrip(trips));
            }
            case 2 -> { // Total cost of a trip
                showAll(trips, new Trip());
                System.out.println("Enter the ID of the trip you want to see the total cost of: ");
                String tripID = scanner.next();
                int tripIndex = -1;

                for (int i = 0; i < trips.length; i++) {
                    if (trips[i] == null) { // Once trips become null, that means we have iterated through all valid elements
                        break;
                    }

                    if (trips[i].getTripID().equals(tripID)) {
                        tripIndex = i;
                        break;
                    }
                }

                if (tripIndex == -1) { // So we break out completely out of the switch case in case trip is not found
                    break;
                }

                double basePrice = trips[tripIndex].getBasePrice();
                double transportationPrice = trips[tripIndex].getTransportation().calculateCost();
                double accommodationPrice = trips[tripIndex].getAccommodation().calculateCost();
                System.out.println("The total cost for this trip is " + (basePrice + transportationPrice) + "$ with a fee of "
                + accommodationPrice + "$ per night.");

            }
            case 3 -> {
                Transportation[] newTransportations = transportationsDeepCopy(transportations);

                // Printing Deep Copied Array
                for (int i = 0; i < newTransportations.length; i++) {
                    System.out.println(newTransportations[i]);
                }
            }
             case 4 -> {
                 Accommodation[] newAccommodations = accommodationsDeepCopy(accommodations);

                 // Printing Deep Copied Array
                 for (int i = 0; i < newAccommodations.length; i++) {
                     System.out.println(newAccommodations[i]);
                 }
             }
            default -> System.out.println("Invalid Input");
        }
    }

    // Adds an object to an array
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

    /*public static void addClient(Client[] clients, Client client) throws DuplicateEmailException {
        if (clients[clients.length - 1] != null) {
            System.out.println("Storage Full.");
        }
        else {
            for (int i = 0; i < clients.length; i++) { // We iterate until we reach a null element
                if (clients[i] == null) {
                    clients[i] = client;
                    break;
                }
            }
        }
    }*/

    public static void deleteClient(Client[] clients, String id) {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                break; // We kill the loop once we reach a null object because it means that we reached the end of the partiall filled array
            }

            if (clients[i].getClientID().equals(id)) {
                clients[i] = null; // Deleting the client if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < clients.length - 1; j++) { // We are going till the before last object in the array
                    clients[j] = clients[j + 1];
                }
                clients[clients.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null

                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            System.out.println("No Clients under that ID exists.");
        }
    }

    public static void deleteTrip(Trip[] trips, String id) throws EntityNotFoundException {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < trips.length; i++) {
            if (trips[i] == null) {
                break; // We kill the loop once we reach a null object because it means that we reached the end of the partiall filled array
            }

            if (trips[i].getTripID().equals(id)) {
                trips[i] = null; // Deleting the trip if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < trips.length - 1; j++) { // We are going till the before last object in the array
                    trips[j] = trips[j + 1];
                }
                trips[trips.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null

                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            throw new EntityNotFoundException("Entity not found.");
        }
    }

    public static void deleteTransportation(Transportation[] transportations, String id) {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < transportations.length; i++) {
            if (transportations[i] == null) {
                break; // We kill the loop once we reach a null object because it means that we reached the end of the partially filled array
            }

            if (transportations[i].getTransportationID().equals(id)) {
                transportations[i] = null; // Deleting the transportations if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < transportations.length - 1; j++) { // We are going till the before last object in the array
                    transportations[j] = transportations[j + 1];
                }
                transportations[transportations.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null

                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            System.out.println("No Transportations under that ID exists.");
        }
    }

    public static void deleteAccommodation(Accommodation[] accommodations, String id) {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < accommodations.length; i++) {
            if (accommodations[i] == null) {
                break; // We kill the loop once we reach a null object because it means that we reached the end of the partiall filled array
            }

            if (accommodations[i].getAccommodationID().equals(id)) {
                accommodations[i] = null; // Deleting the accommodation if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < accommodations.length - 1; j++) { // We are going till the before last object in the array
                    accommodations[j] = accommodations[j + 1];
                }
                accommodations[accommodations.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null

                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            System.out.println("No Accommodations under that ID exists.");
        }
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

    // Shows all objects from an array of objects
    // Object[] objects takes in any object array
    // Object objects will be used to know which objects we want to print out
    //      For example, if we want to print out only Trains, we would call this method in the following manner:
    //          showAll(transportations, new train)
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
                        System.out.println(invalidClientDataException);
                    }
                }
                case 2 -> {
                    System.out.println("Enter a new last name: ");
                    try {
                        clients[clientIndex].setLastName(scanner.next());
                        System.out.println("Last Name Updated to: " + clients[clientIndex].getLastName());
                    }
                    catch (InvalidClientDataException invalidClientDataException) {
                        System.out.println(invalidClientDataException);
                    }
                }
                case 3 -> {
                    System.out.println("Enter a new email: ");
                    try {
                        clients[clientIndex].setEmail(scanner.next());
                        System.out.println("Email Updated to: " + clients[clientIndex].getEmail());
                    }
                    catch (InvalidClientDataException invalidClientDataException) {
                        System.out.println(invalidClientDataException);
                    }
                }
                case 4 -> running = false;
                default -> System.out.println("Invalid Input. Try Again");
            }
        }
    }

    public static void editTrip(Scanner scanner, Trip[] trips, String id, Client[] clients, Transportation[] transportations, Accommodation[] accommodations) throws EntityNotFoundException{
        // Find Index of selected trip
        int tripIndex = -1;
        for (int i = 0; i < trips.length; i++) {
            if (trips[i] == null) {
                break;
            }
            if (trips[i].getTripID().equals(id)) {
                tripIndex = i;
                break;
            }
        }

        if (tripIndex == -1) {
            throw new EntityNotFoundException("Trip does not exist.");
        }

        if (tripIndex > -1) {
            boolean running = true;
            while (running) {
                System.out.println("""
                        Which section do you want to edit?
                        1 - Destination
                        2 - Duration (in days)
                        3 - Base Price
                        4 - Client going on the trip
                        5 - Transportation
                        6 - Accommodation
                        7 - Finished Editing
                        """);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter a destination: ");
                        trips[tripIndex].setDestination(scanner.next());
                        System.out.println("Destination Updated to: " + trips[tripIndex].getDestination());
                    }
                    case 2 -> {
                        System.out.println("Enter the new duration of the trip: ");
                        try {
                            trips[tripIndex].setDurationInDays(scanner.nextInt());
                            System.out.println("Updated Duration to: " + trips[tripIndex].getDurationInDays() + " days.");
                        }
                        catch (InvalidTripDataException invalidTripDataException) {
                            System.out.println(invalidTripDataException);
                        }
                    }
                    case 3 -> {
                        System.out.println("Enter a new base price: ");
                        try {
                            trips[tripIndex].setBasePrice(scanner.nextInt());
                            System.out.println("Base Price Updated to: " + trips[tripIndex].getBasePrice());
                        }
                        catch (InvalidTripDataException invalidTripDataException) {
                            System.out.println(invalidTripDataException);
                        }
                    }
                    case 4 -> {
                        showAll(clients, new Client());
                        System.out.println("Enter the ID of the new client going on the trip: ");
                        String newClientId = scanner.next();
                        int clientIndex = -1;

                        for (int i = 0; i < clients.length; i++) {
                            if (clients[i] == null) {
                                break;
                            }
                            if (clients[i].getClientID().equals(newClientId)) {
                                clientIndex = i;
                                break;
                            }
                        }

                        if (clientIndex == -1) {
                            throw new EntityNotFoundException("Client does not exist.");
                        }
                        trips[tripIndex].setClientOnTrip(clients[clientIndex]);
                        System.out.println("Profile of the new client going on this trip: ");
                        System.out.println(trips[tripIndex].getClientOnTrip());
                    }
                    case 5 -> {
                        for (int i = 0; i < transportations.length; i++) {
                            if (transportations[i] != null) {
                                System.out.println(transportations[i]);
                            }
                        }
                        System.out.println("Enter the ID of the new transportation: ");
                        String newTransportationId = scanner.next();
                        int transportationIndex = -1;

                        for (int i = 0; i < transportations.length; i++) {
                            if (transportations[i] == null) {
                                break;
                            }
                            if (transportations[i].getTransportationID().equals(newTransportationId)) {
                                transportationIndex = i;
                                break;
                            }
                        }
                        if (transportationIndex == -1) {
                            throw new EntityNotFoundException("Transportation does not exist.");
                        }

                        trips[tripIndex].setTransportation(transportations[transportationIndex]);
                        System.out.println("Information about the new transportation method: ");
                        System.out.println(trips[tripIndex].getTransportation());
                    }
                    case 6 -> {
                        for (int i = 0; i < accommodations.length; i++) {
                            if (accommodations[i] != null) {
                                System.out.println(accommodations[i]);
                            }
                        }
                        System.out.println("Enter the ID of the new accommodation: ");
                        String newAccommodationId = scanner.next();
                        int accommodationIndex = -1;

                        for (int i = 0; i < accommodations.length; i++) {
                            if (accommodations[i] == null) {
                                break;
                            }
                            if (accommodations[i].getAccommodationID().equals(newAccommodationId)) {
                                accommodationIndex = i;
                                break;
                            }
                        }

                        if (accommodationIndex == -1) {
                            throw new EntityNotFoundException("Accommodation not found");
                        }

                        trips[tripIndex].setAccommodation(accommodations[accommodationIndex]);
                        System.out.println("Information about the new accommodation: ");
                        System.out.println(trips[tripIndex].getAccommodation());
                    }
                    case 7 -> running = false;
                    default -> System.out.println("Invalid Input. Try Again");
                }
            }
        }
        else {
            System.out.println("No Trip with that ID exist.");
        }
    }

    public static Transportation[] transportationsDeepCopy(Transportation[] original) {
        Transportation[] newTransportations = new Transportation[original.length];

        // Creating Deep Copy of the Array
        for (int i = 0; i < original.length; i++) {
            if (original[i] == null) {
                break;
            }

            if (original[i] instanceof Bus) {
                try {
                    newTransportations[i] = new Bus((Bus) original[i]);
                }
                catch (InvalidTransportDataException invalidTransportDataException) {
                    System.out.println(invalidTransportDataException);
                }
            }
            else if (original[i] instanceof Flight) {
                newTransportations[i] = new Flight((Flight) original[i]);
            }
            else if (original[i] instanceof Train) {
                newTransportations[i] = new Train((Train) original[i]);
            }
        }
        return newTransportations;
    }

    public static Accommodation[] accommodationsDeepCopy(Accommodation[] accommodations) {
        Accommodation[] newAccommodations = new Accommodation[accommodations.length];

        // Creating Deep Copy of the Array
        for (int i = 0; i < accommodations.length; i++) {
            if (accommodations[i] == null) { // If the first entry of the array is null, then that means the entire array is null
                System.out.println("Empty Array.");
                break;
            }

            if (accommodations[i] instanceof Hostel) {
                try {
                    newAccommodations[i] = new Hostel((Hostel) accommodations[i]);
                }
                catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                    System.out.println(invalidAccommodationDataException);
                }
            }
            else if (accommodations[i] instanceof Hotel) {
                try {
                    newAccommodations[i] = new Hotel((Hotel) accommodations[i]);
                }
                catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                    System.out.println(invalidAccommodationDataException);
                }
            }
        }

        return newAccommodations;
    }

    public static Trip mostExpensiveTrip(Trip[] trips) {
        Trip mostExpensive = null;

        for (int i = 0; i < trips.length; i++) {
            if (mostExpensive == null) { // First iteration is obviously gonna be more expensive than a null object
                mostExpensive = trips[i];
            }
            if (trips[i] != null) {
                double previousTotal = mostExpensive.getBasePrice() + mostExpensive.getTransportation().calculateCost() + mostExpensive.getAccommodation().calculateCost();
                double ithTotal = trips[i].getBasePrice() + trips[i].getTransportation().calculateCost() + trips[i].getAccommodation().calculateCost();
                if (ithTotal > previousTotal) {
                    mostExpensive = trips[i];
                }
            }
        }

        return mostExpensive;
    }

    public static void testingScenario(Client[] clients, Transportation[] transportations, Accommodation[] accommodations, Trip[] trips) {
        // We initially set everything as null so that trip can have access to these variables even if an exception gets caught
        Client client1 = null, client2 = null, client3 = null, client4 = null;
        Bus bus1 = null, bus2 = null;
        Train train1 = null, train2 = null;
        Flight flight1 = null, flight2 = null;
        Hotel hotel1 = null, hotel2 = null;
        Hostel hostel1 = null, hostel2 = null;
        try {
            client1 = new Client("Sahon", "Shaha", "shahsahon@gmail.com");
            client2 = new Client("John", "Doe", "johndoe@gmail.com");
            client3 = new Client("Jane", "Doe", "janedoe@gmail.com");
            client4 = new Client(client3);

            add(clients, client1);
            add(clients, client2);
            add(clients, client3);
            add(clients, client4);
        }
        catch (InvalidClientDataException invalidClientDataException) {
            System.out.println(invalidClientDataException);
        }

        try {
            bus1 = new Bus("CanadaTravels", "Montreal", "Quebec", "STM", 2, 61);
            bus2 = new Bus("AmericaUnited", "Toronto", "New York City", "TTC", 3, 65);

            train1 = new Train("ViaRail Canada", "Montreal", "Vancouver City", "High Speed", "Cabin", 100);
            train2 = new Train("North America Transit", "Boston", "Montreal", "Bullet Train", "Deluxe", 75);

            flight1 = new Flight("Europe Travel Agency", "Montreal", "France", "Air France Canada", 27, 4000);
            flight2 = new Flight("Japan Airways", "Montreal", "Japan", "Air Canada", 31, 3200);

            add(transportations, bus1);
            add(transportations, bus2);
            add(transportations, train1);
            add(transportations, train2);
            add(transportations, flight1);
            add(transportations, flight2);
        }
        catch (InvalidTransportDataException invalidTransportDataException) {
            System.out.println(invalidTransportDataException);
        }

        try {
            hotel1 = new Hotel("Hilton Paris Opera", "France", 457, 5);
            hotel2 = new Hotel("Hotel Fine Sakai", "Japan", 37, 3);

            hostel1 = new Hostel("Marseille Deluxe", "France", 114, 4);
            hostel2 = new Hostel("Tokyo Yasashi", "Japan", 100, 2);

            add(accommodations, hotel1);
            add(accommodations, hotel2);
            add(accommodations, hostel1);
            add(accommodations, hostel2);
        }
        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
            System.out.println(invalidAccommodationDataException);
        }

        try {
            Trip trip1 = new Trip("Japan", 7, 2000, client1, flight2, hostel2);
            Trip trip2 = new Trip("France", 14, 3500, client2, bus2, hotel1);
            Trip trip3 = new Trip("France", 14, 3500, client3, flight1, hostel1);

            add(trips, trip1);
            add(trips, trip2);
            add(trips, trip3);
        }
        catch (InvalidTripDataException invalidTripDataException) {
            System.out.println(invalidTripDataException);
        }

        System.out.println("CLIENTS:");
        showAll(clients, new Client());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRIPS");
        showAll(trips, new Trip());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRANSPORTATIONS");
        for (int i = 0; i < transportations.length; i++) {
            if (transportations[i] == null) {
                break;
            }
            System.out.println(transportations[i]);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("ACCOMMODATIONS");
        for (int i = 0; i < accommodations.length; i++) {
            if (accommodations[i] == null) {
                break;
            }
            System.out.println(accommodations[i]);
        }
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("TESTING EQUALS METHOD");
        System.out.println(train2.equals(hotel1));
        System.out.println(hostel1.equals(hostel2));
        System.out.println(client4.equals(client3));
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("DEMONSTRATING POLYMORPHISM");
        for (int i = 0; i < trips.length; i++) {
            if (trips[i] != null) {
                double basePrice = trips[i].getBasePrice();
                double transportationPrice = trips[i].getTransportation().calculateCost();
                double accommodationPrice = trips[i].getAccommodation().calculateCost();

                System.out.println("The total cost for this trip is " + (basePrice + transportationPrice) + "$ with a fee of "
                        + accommodationPrice + "$ per night.");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("MOST EXPENSIVE TRIP");
        System.out.println(mostExpensiveTrip(trips));
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("DEEP COPY OF TRANSPORTATION ARRAY");
        // There are IDs skipped because
        // they are implicitly being generated when a Trip Object is made due to the Transportation and Accommodation fields having deep copies
        Transportation[] deepCopied = transportationsDeepCopy(transportations);

        try {
            deepCopied[1] = new Flight("VIP Vacations", "Wuyang", "Beijing", "Delta Airlines", 20, 20000);
        }
        catch (InvalidTransportDataException invalidTransportDataException) {
            System.out.println(invalidTransportDataException);
        }

        System.out.println("ORIGINAL ARRAY");
        for (int i = 0; i < transportations.length; i++) {
            if (transportations[i] == null) {
                break;
            }
            System.out.println(transportations[i]);
        }
        System.out.println("COPIED ARRAY");
        for (int i = 0; i < deepCopied.length; i++) {
            if (transportations[i] == null) {
                break;
            }
            System.out.println(deepCopied[i]);
        }
    }
}
