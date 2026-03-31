// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Driver;

import Exceptions.*;
import Persistence.*;
import Service.RecentList;
import Service.SmartTravelService;
import Travel.*;
import Client.*;
import Visualization.DashboardGenerator;
import Visualization.TripChartGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartTravelService sts = new SmartTravelService();
        boolean isMenuRunning = true;

        // Assigning storage for the arrays
        List<Client> clients = SmartTravelService.getClients();
        List<Trip> trips = SmartTravelService.getTrips();
        List<Transportation> transportations = SmartTravelService.getTransportations();
        List<Accommodation> accommodations = SmartTravelService.getAccommodations();

        RecentList<Object> recentList = new RecentList<>(); // TODO ADD TO RECENT LIST FOR EVERY "SHOW" INTERACTION

        // Whenever the application opens, it clears the 'errors.txt' file so that errors from a previous run doesn't carry over
        ErrorLogger.clear();

        System.out.println("Welcome to SmartTravel!");

        while (isMenuRunning) {
            try { // This try-catch block is for scenarios where the user enters a string rather than a number
                try {
                    System.out.println("""
                            1. Client Management
                            2. Trip Management
                            3. Transportation Management
                            4. Accommodation Management
                            5. Additional Operations
                            6. Generate Diagrams
                            7. List All Data
                            8. Save All Data
                            9. Load All Data
                            10. Run Predefined Scenario
                            11. Generate HTML Dashboard
                            0. Exit
                            """);
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 0 -> {
                            System.out.println("Closing Application...Goodbye!");
                            isMenuRunning = false;
                        }
                        case 1 -> clientManagement(scanner, clients);
                        case 2 -> tripManagement(scanner, trips, clients, transportations, accommodations);
                        case 3 -> transportationManagement(scanner, transportations);
                        case 4 -> accommodationManagement(scanner, accommodations);
                        case 5 -> additionalOperations(scanner, trips, transportations, accommodations);
                        case 6 -> {
                            try {
                                TripChartGenerator.generateCostBarChart(trips.toArray(new Trip[0]), trips.size());
                                TripChartGenerator.generateDurationLineChart(trips.toArray(new Trip[0]), trips.size());
                                TripChartGenerator.generateDestinationPieChart(trips.toArray(new Trip[0]), trips.size());
                            } catch (IOException e) {
                                System.out.println("Error: " + e);
                            }
                        }
                        case 7-> {
                            System.out.println("==============================CLIENTS==============================");
                            SmartTravelService.showAll(clients);
                            System.out.println("==============================TRANSPORTATIONS==============================");
                            SmartTravelService.showAll(transportations);
                            SmartTravelService.showAll(transportations);
                            SmartTravelService.showAll(transportations);
                            System.out.println("==============================ACCOMMODATIONS==============================");
                            SmartTravelService.showAll(accommodations);
                            SmartTravelService.showAll(accommodations);
                            System.out.println("==============================TRIPS==============================");
                            SmartTravelService.showAll(trips);
                        }
                        case 8 -> {
                            try {
                                SmartTravelService.saveAllData("output/data/");
                            } catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                        case 9 -> {
                            try {
                                SmartTravelService.loadAllData("output/data/");
                            } catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                        case 10 -> testingScenario(clients, transportations, accommodations, trips);
                        case 11 -> {
                            try {
                                DashboardGenerator.generateDashboard(sts);
                            }
                            catch (IOException io) {
                                System.out.println(io.getMessage());
                            }
                        }
                        default -> System.out.println("Invalid Option.");
                    }
                }
                catch (InputMismatchException inputMismatchException) {
                    System.out.println("Invalid Input. Enter a number.");
                    try {
                        ErrorLogger.log(inputMismatchException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                    scanner.nextLine();
                }
            }
            catch (InputMismatchException inputMismatchException) {
                System.out.println("Invalid Input. Enter a number.");
                scanner.nextLine();
            }
        }
    }

    // Handles the Client Menu
    public static void clientManagement(Scanner scanner, List<Client> clients) {
        try {
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
                        for (Client client : clients) {
                            /*if (clients[i] == null) {
                                break;
                            }*/
                            if (client.getEmail().equals(email)) {
                                throw new DuplicateEmailException();
                            }
                        }
                        Client newClient = new Client(firstName, lastName, email);
                        //SmartTravelService.add(clients, newClient);
                        clients.add(newClient);
                    } catch (InvalidClientDataException invalidClientDataException) {
                        //System.out.println("Error: " + invalidClientDataException);
                        System.out.println(invalidClientDataException.getMessage());
                        try {
                            ErrorLogger.log(invalidClientDataException);
                        } catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    } catch (DuplicateEmailException duplicateEmailException) {
                        System.out.println(duplicateEmailException.getMessage());
                        try {
                            ErrorLogger.log(duplicateEmailException);
                        } catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
                case 2 -> { // Editing Clients
                    if (clients.isEmpty()) {
                        System.out.println("No Clients created. Please create some first.");
                    } else {
                        SmartTravelService.showAll(clients);

                        System.out.println("Choose the ID of a client you want to edit: ");
                        String clientID = scanner.next();
                        int clientEditIndex = -1; // If this stays -1, that means a client of that ID doesn't exist

                        for (int i = 0; i < clients.size(); i++) {
                            if (clients.get(i).getId().equals(clientID)) {
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
                        } catch (EntityNotFoundException entityNotFoundException) {
                            System.out.println(entityNotFoundException.getMessage());
                            try {
                                ErrorLogger.log(entityNotFoundException);
                            } catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                    }
                }
                case 3 -> { // Deleting Clients
                    SmartTravelService.showAll(clients);

                    System.out.println("Choose the ID of a client you want to delete: ");
                    String clientID = scanner.next();

                    // Validating the user choice
                    try {
                            deleteClient(clients, clientID);
                            System.out.println("Updated list of Clients post-deletion:");
                            SmartTravelService.showAll(clients);

                    } catch (EntityNotFoundException entityNotFoundException) {
                        System.out.println(entityNotFoundException.getMessage());
                        try {
                            ErrorLogger.log(entityNotFoundException);
                        } catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
                case 4 -> SmartTravelService.showAll(clients);
                default -> System.out.println("Invalid Input.");
            }
        }
        catch (InputMismatchException inputMismatchException) {
            System.out.println("Invalid Input. Enter a number.");
            try {
                ErrorLogger.log(inputMismatchException);
            }
            catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    // Handles the Trip Menu
    public static void tripManagement(Scanner scanner, List<Trip> trips, List<Client> clients, List<Transportation> transportations, List<Accommodation> accommodations) {
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
                SmartTravelService.showAll(clients);
                String clientId = scanner.next();

                System.out.println("Enter the ID of one of the following transportations the client will take: ");
                SmartTravelService.showAll(transportations);
                String transportationId = scanner.next();


                System.out.println("Enter the ID of one of the following accommodations the client will take: ");
                SmartTravelService.showAll(accommodations);
                String accommodationId = scanner.next();

                int clientIndex = -1;
                int transportIndex = -1;
                int accommodationIndex = -1;

                // LOCATING INDEX OF SELECTED CLIENT, TRANSPORTATION AND ACCOMMODATION
                /*for (int i = 0; i < clients.length; i++) {
                    if (clients[i] == null) {
                        break;
                    }
                    if (clients[i].getId().equals(clientId)) {
                        clientIndex = i;
                        break;
                    }
                }*/
                clientIndex = SmartTravelService.findById(clients, clientId);

                /*for (int i = 0; i < transportations.length; i++) {
                    if (transportations[i] == null) {
                        break;
                    }
                    if (transportations[i].getId().equals(transportationId)) {
                        transportIndex = i;
                        break;
                    }
                }*/
                transportIndex = SmartTravelService.findById(transportations, transportationId);

                /*for (int i = 0; i < accommodations.length; i++) {
                    if (accommodations[i] == null) {
                        break;
                    }
                    if (accommodations[i].getId().equals(accommodationId)) {
                        accommodationIndex = i;
                        break;
                    }
                }*/
                accommodationIndex = SmartTravelService.findById(accommodations, accommodationId);

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

                    Trip trip = new Trip(destination, duration, basePrice, clients.get(clientIndex), transportations.get(transportIndex), accommodations.get(accommodationIndex));
                    //SmartTravelService.add(trips, trip);
                    trips.add(trip);
                }
                catch (EntityNotFoundException | InvalidTripDataException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            }
            case 2 -> { // Editing Trip
                if (trips.isEmpty()) {
                    System.out.println("No Trips created. Please create some first.");
                }
                else {
                    SmartTravelService.showAll(trips);

                    System.out.println("Enter the ID of the trip you want to edit: ");
                    String id = scanner.next();
                    try {
                        editTrip(scanner, trips, id, clients, transportations, accommodations);
                    }
                    catch (EntityNotFoundException entityNotFoundException) {
                        System.out.println(entityNotFoundException.getMessage());
                        try {
                            ErrorLogger.log(entityNotFoundException);
                        }
                        catch (IOException ioException) {
                            System.out.println(ioException.getMessage());
                        }
                    }
                }
            }
            case 3 -> { // Deleting Trip
                SmartTravelService.showAll(trips);
                System.out.println("Choose the ID of the trip you want to delete: ");
                String id = scanner.next();
                try {
                    deleteTrip(trips, id);
                    System.out.println("Updated list of Trips post-deletion:");
                    SmartTravelService.showAll(trips);
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }

            }
            case 4 -> SmartTravelService.showAll(trips);
            case 5 -> { // Showing Trips based on a client ID
                SmartTravelService.showAll(clients);
                System.out.println("Enter the ID of the client you want to see the trips of: ");
                String clientID = scanner.next();
                int clientIndex = -1;

                /*for (int i = 0; i < clients.size(); i++) {
                    if (clients[i] != null && clients[i].getId().equals(clientID)) {
                        clientIndex = i;
                    }
                }*/
                clientIndex = SmartTravelService.findById(clients, clientID);

                try {
                    if (clientIndex == -1) {
                        throw new EntityNotFoundException("Client does not exist.");
                    }
                    else {
                        for (Trip trip : trips) {
                            // We check if the client of trip at i is the same as the client the user chose
                            if (trip.getClientOnTrip().equals(clients.get(clientIndex))) {
                                System.out.println(trip);
                            }
                        }
                    }
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    // Handles the Transportation Menu
    public static void transportationManagement(Scanner scanner, List<Transportation> transportations) {
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
                            //SmartTravelService.add(transportations, newBus);
                            transportations.add(newBus);
                        }
                        catch (InvalidTransportDataException invalidTransportDataException) {
                            System.out.println(invalidTransportDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidTransportDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
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
                            //SmartTravelService.add(transportations, newFlight);
                            transportations.add(newFlight);
                        }
                        catch (InvalidTransportDataException invalidTransportDataException) {
                            System.out.println(invalidTransportDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidTransportDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
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
                         //SmartTravelService.add(transportations, newTrain);
                         transportations.add(newTrain);
                     }
                    default -> System.out.println("Invalid Input");
                }
            }

            case 2 -> { // Deleting a transportation
                /*System.out.println("What type of transportation?");
                System.out.println("""
                        1. Bus
                        2. Flight
                        3. Train
                        """);
                int transportationChoice = scanner.nextInt();

                switch (transportationChoice) {
                    case 1 -> SmartTravelService.showAll(transportations, new Bus());
                    case 2 -> SmartTravelService.showAll(transportations, new Flight());
                    case 3 -> SmartTravelService.showAll(transportations, new Train());
                    default -> System.out.println("Invalid Input.");
                }*/
                SmartTravelService.showAll(transportations);
                System.out.println("Choose the ID of a Transportation you want to delete: ");
                String id = scanner.next();
                try {
                    deleteTransportation(transportations, id);
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
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
                    case 1 -> SmartTravelService.showAll(transportations, Bus.class);
                    case 2 -> SmartTravelService.showAll(transportations, Flight.class);
                    case 3 -> SmartTravelService.showAll(transportations, Train.class);
                    case 4 -> SmartTravelService.showAll(transportations);
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    // Handles the Accommodation Menu
    public static void accommodationManagement(Scanner scanner, List<Accommodation> accommodations) {
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
                System.out.println("Enter the amount of nights you will be staying: ");
                int numberOfNights = scanner.nextInt();

                switch (accommodationChoice) {
                    case 1 -> { // Adding a hostel
                        System.out.println("Enter the number of beds per room: ");
                        int beds = scanner.nextInt();

                        try {
                            Hostel newHostel = new Hostel(name, location, pricePerNight, numberOfNights, beds);
                            //SmartTravelService.add(accommodations, newHostel);
                            accommodations.add(newHostel);
                        }
                        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                            System.out.println(invalidAccommodationDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidAccommodationDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                    }
                    case 2 -> { // Adding a hotel
                        System.out.println("Enter the number of stars: ");
                        int stars = scanner.nextInt();

                        try {
                            Hotel newHotel = new Hotel(name, location, pricePerNight, numberOfNights, stars);
                            //SmartTravelService.add(accommodations, newHotel);
                            accommodations.add(newHotel);
                        }
                        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                            System.out.println(invalidAccommodationDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidAccommodationDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            case 2 -> { // Deleting an accommodation
                /*System.out.println("What type of accommodation?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        """);
                int accommodationChoice = scanner.nextInt();

                switch (accommodationChoice) {
                    case 1 -> SmartTravelService.showAll(accommodations, new Hostel());
                    case 2 -> SmartTravelService.showAll(accommodations, new Hotel());
                    default -> System.out.println("Invalid Input.");
                }*/
                SmartTravelService.showAll(accommodations);

                System.out.println("Choose the ID of an Accommodation you want to delete: ");
                String id = scanner.next();
                try {
                    deleteAccommodation(accommodations, id);
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
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
                    case 1 -> SmartTravelService.showAll(accommodations, Hostel.class);
                    case 2 -> SmartTravelService.showAll(accommodations, Hotel.class);
                    case 3 -> SmartTravelService.showAll(accommodations);
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    // Handles additional operations
    public static void additionalOperations(Scanner scanner, List<Trip> trips, List<Transportation> transportations, List<Accommodation> accommodations) {
        System.out.println("""
                1. Display the Most Expensive Trip
                2. Display the Total Cost of a Trip
                3. Create a Deep Copy of the Transportation Array
                4. Create a Deep Copy of the Accommodation Array
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                try {
                    System.out.println(mostExpensiveTrip(trips));
                }
                catch (EntityNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> { // Total cost of a trip
                SmartTravelService.showAll(trips);
                System.out.println("Enter the ID of the trip you want to see the total cost of: ");
                String tripID = scanner.next();
                int tripIndex = -1;
                tripIndex = SmartTravelService.findById(trips, tripID);
                /*for (int i = 0; i < trips.size(); i++) {
                    if (trips[i].getId().equals(tripID)) {
                        tripIndex = i;
                        break;
                    }
                }*/

                try {
                    if (tripIndex == -1) {
                        throw new EntityNotFoundException("Trip does not exist.");
                    }

                    // Summing all prices
                    double basePrice = trips.get(tripIndex).getBasePrice();
                    double transportationPrice = trips.get(tripIndex).getTransportation().calculateCost();
                    double accommodationPrice = trips.get(tripIndex).getAccommodation().calculateCost();
                    System.out.println("The total cost for this trip is " + (basePrice + transportationPrice) + "$ with a fee of "
                            + accommodationPrice + "$ per night.");
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    try {
                        ErrorLogger.log(entityNotFoundException);
                    }
                    catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            }
            case 3 -> {
                /*Transportation[] newTransportations = transportationsDeepCopy(transportations);

                // Printing Deep Copied Array
                for (int i = 0; i < newTransportations.length; i++) {
                    System.out.println(newTransportations[i]);
                }
                */
                SmartTravelService.showAll(transportationsDeepCopy(transportations));
            }
             case 4 -> {
                 /*Accommodation[] newAccommodations = accommodationsDeepCopy(accommodations);

                 // Printing Deep Copied Array
                 for (int i = 0; i < newAccommodations.length; i++) {
                     System.out.println(newAccommodations[i]);
                 }*/
                 SmartTravelService.showAll(accommodationsDeepCopy(accommodations));
             }
            default -> System.out.println("Invalid Input");
        }
    }

    // Deletes a client by searching through an array using an id.
    // The deletion is done by shifting back every client after the target.
    // This goes for all the following deletion methods
    public static void deleteClient(List<Client> clients, String id) throws EntityNotFoundException {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(id)) {
                clients.remove(i); // Deleting the client if it matches the id
                deleted = true;

                /*// Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < clients.length - 1; j++) { // We are going till the before last object in the array
                    clients[j] = clients[j + 1];
                }
                clients[clients.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null
*/
                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            throw new EntityNotFoundException("Client of that ID does not exist");
        }
    }

    public static void deleteTrip(List<Trip> trips, String id) throws EntityNotFoundException {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getId().equals(id)) {
                /*trips[i] = null; // Deleting the trip if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < trips.length - 1; j++) { // We are going till the before last object in the array
                    trips[j] = trips[j + 1];
                }
                trips[trips.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null
*/
                trips.remove(i);
                deleted = true;
                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            throw new EntityNotFoundException("Entity not found.");
        }
    }

    public static void deleteTransportation(List<Transportation> transportations, String id) throws EntityNotFoundException {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < transportations.size(); i++) {
            if (transportations.get(i).getId().equals(id)) {
                /*transportations[i] = null; // Deleting the transportations if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < transportations.length - 1; j++) { // We are going till the before last object in the array
                    transportations[j] = transportations[j + 1];
                }
                transportations[transportations.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null
*/
                transportations.remove(i);
                deleted = true;
                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            throw new EntityNotFoundException("Transportation does not exist.");
        }
    }

    public static void deleteAccommodation(List<Accommodation> accommodations, String id) throws EntityNotFoundException {
        boolean deleted = false; // This is used to see if something got deleted or not

        for (int i = 0; i < accommodations.size(); i++) {
            if (accommodations.get(i).getId().equals(id)) {
                /*accommodations[i] = null; // Deleting the accommodation if it matches the id
                deleted = true;

                // Shifting the array back so that the array does not have a "hole" in the middle
                for (int j = i; j < accommodations.length - 1; j++) { // We are going till the before last object in the array
                    accommodations[j] = accommodations[j + 1];
                }
                accommodations[accommodations.length - 1] = null; // no matter what, if an object is deleted and everything is shifted, the last object MUST be null
*/
                accommodations.remove(i);
                deleted = true;
                break; // Kill the loop because there is no reason to keep searching
            }
        }

        if (!deleted) {
            throw new EntityNotFoundException("Accommodation does not exist.");
        }
    }

    // Handles the Client Editing Submenu
    public static void editClient(Scanner scanner, List<Client> clients, int clientIndex) {
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
                        clients.get(clientIndex).setFirstName(scanner.next());
                        System.out.println("First Name Updated to: " + clients.get(clientIndex).getFirstName());
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
                        clients.get(clientIndex).setLastName(scanner.next());
                        System.out.println("Last Name Updated to: " + clients.get(clientIndex).getLastName());
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
                        clients.get(clientIndex).setEmail(scanner.next());
                        System.out.println("Email Updated to: " + clients.get(clientIndex).getEmail());
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

    // Handles the Trip Editing Submenu
    public static void editTrip(Scanner scanner, List<Trip> trips, String id, List<Client> clients, List<Transportation> transportations, List<Accommodation> accommodations) throws EntityNotFoundException{
        // Find Index of selected trip
        int tripIndex = -1;
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getId().equals(id)) {
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
                        trips.get(tripIndex).setDestination(scanner.next());
                        System.out.println("Destination Updated to: " + trips.get(tripIndex).getDestination());
                    }
                    case 2 -> {
                        System.out.println("Enter the new duration of the trip: ");
                        try {
                            trips.get(tripIndex).setDurationInDays(scanner.nextInt());
                            System.out.println("Updated Duration to: " + trips.get(tripIndex).getDurationInDays() + " days.");
                        }
                        catch (InvalidTripDataException invalidTripDataException) {
                            System.out.println(invalidTripDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidTripDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("Enter a new base price: ");
                        try {
                            trips.get(tripIndex).setBasePrice(scanner.nextInt());
                            System.out.println("Base Price Updated to: " + trips.get(tripIndex).getBasePrice());
                        }
                        catch (InvalidTripDataException invalidTripDataException) {
                            System.out.println(invalidTripDataException.getMessage());
                            try {
                                ErrorLogger.log(invalidTripDataException);
                            }
                            catch (IOException ioException) {
                                System.out.println(ioException.getMessage());
                            }
                        }
                    }
                    case 4 -> {
                        SmartTravelService.showAll(clients);
                        System.out.println("Enter the ID of the new client going on the trip: ");
                        String newClientId = scanner.next();
                        int clientIndex = SmartTravelService.findById(clients, newClientId);

                        if (clientIndex == -1) {
                            throw new EntityNotFoundException("Client does not exist.");
                        }
                        trips.get(tripIndex).setClientOnTrip(clients.get(clientIndex));
                        System.out.println("Profile of the new client going on this trip: ");
                        System.out.println(trips.get(tripIndex).getClientOnTrip());
                    }
                    case 5 -> {
                        SmartTravelService.showAll(transportations);
                        System.out.println("Enter the ID of the new transportation: ");
                        String newTransportationId = scanner.next();
                        int transportationIndex = -1;

                        for (int i = 0; i < transportations.size(); i++) {
                            if (transportations.get(i).getId().equals(newTransportationId)) {
                                transportationIndex = i;
                                break;
                            }
                        }
                        if (transportationIndex == -1) {
                            throw new EntityNotFoundException("Transportation does not exist.");
                        }

                        trips.get(tripIndex).setTransportation(transportations.get(transportationIndex));
                        System.out.println("Information about the new transportation method: ");
                        System.out.println(trips.get(tripIndex).getTransportation());
                    }
                    case 6 -> {
                        SmartTravelService.showAll(accommodations);
                        System.out.println("Enter the ID of the new accommodation: ");
                        String newAccommodationId = scanner.next();
                        int accommodationIndex = -1;

                        for (int i = 0; i < accommodations.size(); i++) {
                            if (accommodations.get(accommodationIndex).getId().equals(newAccommodationId)) {
                                accommodationIndex = i;
                                break;
                            }
                        }

                        if (accommodationIndex == -1) {
                            throw new EntityNotFoundException("Accommodation not found");
                        }

                        trips.get(tripIndex).setAccommodation(accommodations.get(accommodationIndex));
                        System.out.println("Information about the new accommodation: ");
                        System.out.println(trips.get(tripIndex).getAccommodation());
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


    // DEEP COPY METHODS
    public static List<Transportation> transportationsDeepCopy(List<Transportation> original) {
        List<Transportation> newTransportations = new ArrayList<>();

        // Creating Deep Copy of the Array
        for (Transportation transportation : original) {
            // Type Checking so we can use the proper copy method for the respective class
            try {
                if (transportation instanceof Bus) {
                    newTransportations.add(new Bus((Bus) transportation));
                } else if (transportation instanceof Flight) {
                    newTransportations.add(new Flight((Flight) transportation));
                } else if (transportation instanceof Train) {
                    newTransportations.add(new Train((Train) transportation));
                }
            } catch (InvalidTransportDataException invalidTransportDataException) {
                System.out.println(invalidTransportDataException.getMessage());
                try {
                    ErrorLogger.log(invalidTransportDataException);
                } catch (IOException io) {
                    System.out.println(io.getMessage());
                }
            }
        }
        return newTransportations;
    }

    public static List<Accommodation> accommodationsDeepCopy(List<Accommodation> accommodations) {
        List<Accommodation> newAccommodations = new ArrayList<>();

        // Creating Deep Copy of the Array
        for (Accommodation accommodation : accommodations) {
            if (accommodation instanceof Hostel) {
                try {
                    newAccommodations.add(new Hostel((Hostel) accommodation));
                } catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                    System.out.println(invalidAccommodationDataException.getMessage());
                    try {
                        ErrorLogger.log(invalidAccommodationDataException);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            } else if (accommodation instanceof Hotel) {
                try {
                    newAccommodations.add(new Hotel((Hotel) accommodation));
                } catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                    System.out.println(invalidAccommodationDataException.getMessage());
                    try {
                        ErrorLogger.log(invalidAccommodationDataException);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            }
        }

        return newAccommodations;
    }


    // Cycles through all trips and compares them till it finds the most expensive trip
    public static Trip mostExpensiveTrip(List<Trip> trips) throws EntityNotFoundException {
        if (trips.isEmpty()) {
            throw new EntityNotFoundException("No trips have been initialized");
        }

        int mostExpensive = -1;

        for (int i = 0; i < trips.size(); i++) {
            if (mostExpensive == -1) { // Initial Set
                mostExpensive = i;
            }

            double ithTotal = SmartTravelService.calculateTripTotal(i);
            double previousTotal = SmartTravelService.calculateTripTotal(mostExpensive);
            if (ithTotal > previousTotal) {
                mostExpensive = i;
            }

        }

        return trips.get(mostExpensive);
    }

    public static void testingScenario(List<Client> clients, List<Transportation> transportations, List<Accommodation> accommodations, List<Trip> trips) {
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

            /*SmartTravelService.add(clients, client1);
            SmartTravelService.add(clients, client2);
            SmartTravelService.add(clients, client3);
            SmartTravelService.add(clients, client4);*/
            clients.add(client1);
            clients.add(client2);
            clients.add(client3);
            clients.add(client4);
        }
        catch (InvalidClientDataException invalidClientDataException) {
            System.out.println(invalidClientDataException.getMessage());
        }

        // CLIENT EXCEPTION HANDLING SHOWCASE
        // Name too long
        try {
            Client badClient = new Client("A".repeat(51), "Smith", "smith@gmail.com");
            clients.add(badClient);
        } catch (InvalidClientDataException e) {
            System.out.println(e.getMessage());
        }

        // Invalid email (missing @)
        try {
            Client badClient = new Client("Bob", "Smith", "bobsmithgmail.com");
            clients.add(badClient);
        } catch (InvalidClientDataException e) {
            System.out.println(e.getMessage());
        }

        // Duplicate Email Exception
        try {
            Client duplicateClient = new Client("Another", "Sahon", "shahsahon@gmail.com"); // same email as client1
            // Check for duplicate before adding
            for (Client client : clients) {
                if (client.getEmail().equals(duplicateClient.getEmail())) {
                    throw new DuplicateEmailException();
                }
            }
            clients.add(duplicateClient);
        } catch (InvalidClientDataException | DuplicateEmailException e) {
            System.out.println(e.getMessage());
        }

        try {
            bus1 = new Bus("CanadaTravels", "Montreal", "Quebec", "STM", 2, 61);
            bus2 = new Bus("AmericaUnited", "Toronto", "New York City", "TTC", 3, 65);

            train1 = new Train("ViaRail Canada", "Montreal", "Vancouver City", "High Speed", "Cabin", 100);
            train2 = new Train("North America Transit", "Boston", "Montreal", "Bullet Train", "Deluxe", 75);

            flight1 = new Flight("Europe Travel Agency", "Montreal", "France", "Air France Canada", 27, 4000);
            flight2 = new Flight("Japan Airways", "Montreal", "Japan", "Air Canada", 31, 3200);

            /*SmartTravelService.add(transportations, bus1);
            SmartTravelService.add(transportations, bus2);
            SmartTravelService.add(transportations, train1);
            SmartTravelService.add(transportations, train2);
            SmartTravelService.add(transportations, flight1);
            SmartTravelService.add(transportations, flight2);*/

            transportations.add(bus1);
            transportations.add(bus2);
            transportations.add(train1);
            transportations.add(train2);
            transportations.add(flight1);
            transportations.add(flight2);
        }
        catch (InvalidTransportDataException invalidTransportDataException) {
            System.out.println(invalidTransportDataException.getMessage());
            try {
                ErrorLogger.log(invalidTransportDataException);
            }
            catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }

        // TRANSPORTATION EXCEPTIONS SHOWCASE
        // Negative Luggage Allowance
        try {
            Flight badFlight = new Flight("Bad Airways", "Montreal", "Tokyo", "Air Bad", -5, 3000);
            transportations.add(badFlight);
        } catch (InvalidTransportDataException e) {
            System.out.println(e.getMessage());
        }

        // Bus with 0 stops
        try {
            Bus badBus = new Bus("BadBus", "Montreal", "Quebec", "STM", 0, 50);
            transportations.add(badBus);
        } catch (InvalidTransportDataException e) {
            System.out.println(e.getMessage());
        }

        try {
            hotel1 = new Hotel("Hilton Paris Opera", "France", 457, 4, 5);
            hotel2 = new Hotel("Hotel Fine Sakai", "Japan", 37, 7, 3);

            hostel1 = new Hostel("Marseille Deluxe", "France", 114, 1, 4);
            hostel2 = new Hostel("Tokyo Yasashi", "Japan", 100, 2, 2);

            /*SmartTravelService.add(accommodations, hotel1);
            SmartTravelService.add(accommodations, hotel2);
            SmartTravelService.add(accommodations, hostel1);
            SmartTravelService.add(accommodations, hostel2);*/
            accommodations.add(hotel1);
            accommodations.add(hotel2);
            accommodations.add(hostel1);
            accommodations.add(hostel2);
        }
        catch (InvalidAccommodationDataException invalidAccommodationDataException) {
            System.out.println(invalidAccommodationDataException.getMessage());
            try {
                ErrorLogger.log(invalidAccommodationDataException);
            }
            catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }

        // ACCOMMODATION EXCEPTION SHOWCASE
        // Price per night = 0
        try {
            Hotel badHotel = new Hotel("Free Hotel", "France", 0, 3, 4);
            accommodations.add(badHotel);
        } catch (InvalidAccommodationDataException e) {
            System.out.println(e.getMessage());
        }

        // Invalid star rating
        try {
            Hotel badHotel = new Hotel("Six Star Hotel", "France", 500, 3, 6);
            accommodations.add(badHotel);
        } catch (InvalidAccommodationDataException e) {
            System.out.println(e.getMessage());
        }

        // Hostel price > $150 (business rule violation)
        try {
            Hostel badHostel = new Hostel("Luxury Hostel", "Japan", 200, 2, 3);
            accommodations.add(badHostel);
        } catch (InvalidAccommodationDataException e) {
            System.out.println(e.getMessage());
        }

        try {
            Trip trip1 = new Trip("Japan", 7, 2000, client1, flight2, hostel2);
            Trip trip2 = new Trip("France", 14, 3500, client2, bus2, hotel1);
            Trip trip3 = new Trip("France", 14, 3500, client3, flight1, hostel1);

            /*SmartTravelService.add(trips, trip1);
            SmartTravelService.add(trips, trip2);
            SmartTravelService.add(trips, trip3);*/
            trips.add(trip1);
            trips.add(trip2);
            trips.add(trip3);
        }
        catch (InvalidTripDataException invalidTripDataException) {
            System.out.println(invalidTripDataException.getMessage());
        }

        // TRIP EXCEPTION SHOWCASE
        // Base price below $100
        try {
            Trip badTrip = new Trip("Japan", 7, 50, client1, flight2, hostel2);
            trips.add(badTrip);
        } catch (InvalidTripDataException e) {
            System.out.println(e.getMessage());
        }

        // Duration out of range
        try {
            Trip badTrip = new Trip("Japan", 25, 2000, client1, flight2, hostel2);
            trips.add(badTrip);
        } catch (InvalidTripDataException e) {
            System.out.println(e.getMessage());
        }

        // ENTITYNOTFOUND EXCEPTION SHOWCASE
        try {
            // Search for a client ID that doesn't exist
            String fakeID = "C9999";
            if (SmartTravelService.findById(clients, fakeID) == -1) {
                throw new EntityNotFoundException("No client found with ID: " + fakeID);
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("CLIENTS:");
        SmartTravelService.showAll(clients);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRIPS");
        SmartTravelService.showAll(trips);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRANSPORTATIONS");
        SmartTravelService.showAll(transportations);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("ACCOMMODATIONS");
        SmartTravelService.showAll(accommodations);
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("TESTING EQUALS METHOD");
        System.out.println(train2.equals(hotel1));
        System.out.println(hostel1.equals(hostel2));
        System.out.println(client4.equals(client3));
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("DEMONSTRATING POLYMORPHISM");
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i) != null) {
                System.out.println("The total cost for this trip is " + (SmartTravelService.calculateTripTotal(i)));
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("MOST EXPENSIVE TRIP");
        try {
            System.out.println(mostExpensiveTrip(trips));
        }
        catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("DEEP COPY OF TRANSPORTATION ARRAY");
        List<Transportation> deepCopied = transportationsDeepCopy(transportations);

        try {
            deepCopied.add(1, new Flight("VIP Vacations", "Wuyang", "Beijing", "Delta Airlines", 20, 20000));
        }
        catch (InvalidTransportDataException invalidTransportDataException) {
            System.out.println(invalidTransportDataException.getMessage());
            try {
                ErrorLogger.log(invalidTransportDataException);
            }
            catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }

        System.out.println("ORIGINAL ARRAY");
        SmartTravelService.showAll(transportations);
        System.out.println("COPIED ARRAY");
        SmartTravelService.showAll(deepCopied);
    }
}
