// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

// TODO ADD DEFAULT CASES TO ALL SWITCHCASE
package Driver;

import Client.Client;
import Travel.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMenuRunning = true;

        // Creating storage for the arrays
        // TODO Transportation Array = Buses + Trains + Flights
        // TODO Accommodation Array = Hotels + Hostels
        Client[] clients = new Client[99];
        Trip[] trips = new Trip[99];
        Bus[] buses = new Bus[99];
        Train[] trains = new Train[99];
        Flight[] flights = new Flight[99];
        Hotel[] hotels = new Hotel[99];
        Hostel[] hostels = new Hostel[99];


        System.out.println("Welcome to SmartTravel!");
        System.out.println("""
                Would you like to use the application or run the testing scenario?
                1 - Run Application
                2 - Execute Testing Scenario
                """);
        int initialChoice = scanner.nextInt();

        while (isMenuRunning) {
            switch (initialChoice) {
                case 1 -> {
                    System.out.println("""
                        1. Client Management
                        2. Trip Management
                        3. Transportation Management
                        4. Accommodation Management
                        5. Additional Operations
                        """);
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 -> clientManagement(scanner, clients);
                        case 2 -> tripManagement(scanner, trips, clients);
                        case 3 -> transportationManagement(scanner, buses, trains, flights);
                        case 4 -> accommodationManagement(scanner, hostels, hotels);
                        case 5 -> {

                        }
                        default -> System.out.println("Invalid Option.");
                    }
                }

                case 2 -> {testingScenario(); isMenuRunning = false;}
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
            case 1 -> {
                System.out.println("Enter you First Name: ");
                String firstName = scanner.next();
                System.out.println("Enter your Last Name: ");
                String lastName = scanner.next();
                System.out.println("Enter your Email: ");
                String email = scanner.next();

                Client newClient = new Client(firstName, lastName, email);
                add(clients, newClient);
            }
            case 2 -> {

                if (clients[0] == null) {
                    System.out.println("No Clients created. Please create some first.");
                }
                else {
                    showAll(clients, new Client());

                    System.out.println("Choose the number of a client you want to edit: ");
                    int clientEditIndex = scanner.nextInt() - 1;

                    // Validating the user choice
                    if (clients[clientEditIndex] == null) {
                        System.out.println("Invalid Client Chosen");
                    }
                    else {
                        editClient(scanner, clients, clientEditIndex);
                    }
                }
            }
            case 3 -> {
                showAll(clients, new Client());

                System.out.println("Choose the number of a client you want to delete: ");
                int clientDeleteIndex = scanner.nextInt() - 1;

                if (clients[clientDeleteIndex] == null) {
                    System.out.println("Invalid Client Chosen");
                }
                else {
                    delete(clients, clientDeleteIndex);
                }
            }
            case 4 -> showAll(clients, new Client());
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void tripManagement(Scanner scanner, Trip[] trips, Client[] clients) {
        System.out.println("""
                1. Create a trip
                2. Edit trip information
                3. Cancel a trip
                4. List all trips
                5. List all trips for a specific client
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println("Enter the destination: ");
                String destination = scanner.next();
                System.out.println("Enter the duration of the trip (in days): ");
                int duration = scanner.nextInt();
                System.out.println("Enter the base price of the trip: ");
                int basePrice = scanner.nextInt();
                System.out.println("Choose one of the following clients that will be going on this trip: ");
                showAll(clients, new Client());
                int clientIndex = scanner.nextInt();

                Trip trip = new Trip(destination, duration, basePrice, clients[clientIndex - 1]);
                add(trips, trip);
            }
            case 2 -> {

                if (trips[0] == null) {
                    System.out.println("No Trips created. Please create some first.");
                }
                else {
                    showAll(trips, new Trip());

                    System.out.println("Choose the number of a trip you want to edit: ");
                    int tripEditIndex = scanner.nextInt() - 1;

                    // Validating the user choice
                    if (trips[tripEditIndex] == null) {
                        System.out.println("Invalid Trip Chosen");
                    }
                    else {
                        editTrip(scanner, trips, tripEditIndex, clients);
                    }
                }
            }
            case 3 -> {
                showAll(trips, new Trip());

                System.out.println("Choose the number of a trip you want to delete: ");
                int tripDeleteIndex = scanner.nextInt() - 1;

                if (trips[tripDeleteIndex] == null) {
                    System.out.println("Invalid Trip Chosen");
                }
                else {
                    delete(trips, tripDeleteIndex);
                }
            }
            case 4 -> showAll(trips, new Trip());
            case 5 -> {
                showAll(clients, new Client());
                System.out.println("Choose number of the client you want to see the trips of: ");
                int clientIndex = scanner.nextInt() - 1;

                if (clients[clientIndex] == null) { // Ensuring the user doesn't enter the number of a non-existing client
                    System.out.println("No Client of that number exists");
                }
                else {
                    for (int i = 0; i < clients.length; i++) {
                        if (clients[i] == null) {
                            break; // If we reach a null object, that means we've gone through every existing trip
                        }

                        // We check if the client of trip at i is the same as the client the user chose
                        if (trips[i].getClientOnTrip().equals(clients[clientIndex])) {
                            System.out.println(trips[i]);
                        }
                    }
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void transportationManagement(Scanner scanner, Bus[] buses, Train[] trains, Flight[] flights) {
        System.out.println("""
                1. Add a transportation option
                2. Remove a transportation option
                3. List transportation options by type
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
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
                    case 1 -> {
                        System.out.println("Enter the name of the Bus Company: ");
                        String busCompany = scanner.next();
                        System.out.println("Enter the amount of stops during the transit: ");
                        int stops = scanner.nextInt();

                        Bus newBus = new Bus(companyName, departureCity, arrivalCity, busCompany, stops);
                        add(buses, newBus);
                    }

                    case 2 -> {
                        System.out.println("Enter the Airline Name: ");
                        String airlineName = scanner.next();
                        System.out.println("Enter the maximum weight of luggage: ");
                        int weight = scanner.nextInt();

                        Flight newFlight = new Flight(companyName, departureCity, arrivalCity, airlineName, weight);
                        add(flights, newFlight);
                    }

                     case 3 -> {
                         System.out.println("Enter the type of train: ");
                         String trainType = scanner.next();
                         System.out.println("Enter class of seats: ");
                         String seatClass = scanner.next();

                         Train newTrain = new Train(companyName, departureCity, arrivalCity, trainType, seatClass);
                         add(trains, newTrain);
                     }
                }
            }

            case 2 -> {
                System.out.println("What type of transportation?");
                System.out.println("""
                        1. Bus
                        2. Flight
                        3. Train
                        """);
                int transportationChoice = scanner.nextInt();

                switch (transportationChoice) {
                    case 1 -> {
                        showAll(buses, new Bus());

                        System.out.println("Select the number of the transportation you want to delete: ");
                        delete(buses, scanner.nextInt() - 1);
                    }
                    case 2 -> {
                        System.out.println("Select the number of the transportation you want to delete: ");
                        delete(flights, scanner.nextInt() - 1);
                    }

                    case 3 -> {
                        System.out.println("Select the number of the transportation you want to delete: ");
                        delete(trains, scanner.nextInt() - 1);
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }

            case 3 -> {
                System.out.println("Which type of transportation do you want to list?");
                System.out.println("""
                        1. Buses
                        2. Flights
                        3. Trains
                        4. All
                        """);
                int listViewChoice = scanner.nextInt();

                switch (listViewChoice) {
                    case 1 -> showAll(buses, new Bus());
                    case 2 -> showAll(flights, new Flight());
                    case 3 -> showAll(trains, new Train());
                    case 4 -> {
                        System.out.println("BUSES");
                        showAll(buses, new Bus());
                        System.out.println("FLIGHTS");
                        showAll(flights, new Flight());
                        System.out.println("TRAINS");
                        showAll(trains, new Train());
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void accommodationManagement(Scanner scanner, Hostel[] hostels, Hotel[] hotels) {
        System.out.println("""
                1. Add an accommodation
                2. Remove an accommodation
                3. List accommodations by type
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
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
                    case 1 -> {
                        System.out.println("Enter the number of beds per room: ");
                        int beds = scanner.nextInt();

                        Hostel newHostel = new Hostel(name, location, pricePerNight, beds);
                        add(hostels, newHostel);
                    }
                    case 2 -> {
                        System.out.println("Enter the number of stars: ");
                        int stars = scanner.nextInt();

                        Hotel newHotel = new Hotel(name, location, pricePerNight, stars);
                        add(hotels, newHotel);
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            case 2 -> {
                System.out.println("What type of accommodation?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        """);
                int accommodationChoice = scanner.nextInt();

                switch (accommodationChoice) {
                    case 1 -> {
                        showAll(hostels, new Hostel());

                        System.out.println("Select the number of the hostel you want to delete: ");
                        delete(hostels, scanner.nextInt() - 1);
                    }
                    case 2 -> {
                        showAll(hotels, new Hotel());
                        System.out.println("Select the number of the hotel you want to delete: ");
                        delete(hotels, scanner.nextInt() - 1);
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            case 3 -> {
                System.out.println("Which type of transportation do you want to list?");
                System.out.println("""
                        1. Hostels
                        2. Hotels
                        3. All
                        """);
                int listViewChoice = scanner.nextInt();

                switch (listViewChoice) {
                    case 1 -> showAll(hostels, new Hostel());
                    case 2 -> showAll(hotels, new Hotel());
                    case 3 -> {
                        System.out.println("Hostels");
                        showAll(hostels, new Hostel());
                        System.out.println("Hotels");
                        showAll(hotels, new Hotel());
                    }
                    default -> System.out.println("Invalid Input.");
                }
            }
            default -> System.out.println("Invalid Input.");
        }
    }

    public static void additionalOperations(Scanner scanner) {
        System.out.println("""
                1. Display the Most Expensive Trip
                2. Display the Total Cost of a Trip
                3. Create a Deep Copy of the Transportation Array
                4. Create a Deep Copy of the Accommodation Array
                """);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {

            }
            case 2 -> {

            }
            case 3 -> {

            }
             case 4 -> {

             }
        }
    }

    // Adds an object to an array
    public static void add(Object[] objects, Object object) {
        if (objects[98] != null) {
            System.out.println("Storage Full.");
        }
        else {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] == null) {
                    objects[i] = object;
                    break;
                }
            }
        }
    }

    // Deletes an object at a certain index
    public static void delete(Object[] objects, int clientIndex) {
        objects[clientIndex] = null;

        // Shifting all objects back to clear the gap in the array caused by the deleted object
        for (int i = clientIndex; i < objects.length; i++) {
            // If the next object is null, that means that it's the "end of the array" as everything after that index will be null.
            if (objects[i+1] == null) {
                objects[i] = null; // Since this object has already been shifted back in the last iteration, we just set it as null
                break;
            }
            else {
                objects[i] = objects[i + 1];   // Pushing back objects
            }
        }
    }

    // TODO Make it so it prints only the type of object we want
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
                    System.out.println("\nEntry " + (i+1));
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
                    clients[clientIndex].setFirstName(scanner.next());
                    System.out.println("First Name Updated to: " + clients[clientIndex].getFirstName());
                }
                case 2 -> {
                    System.out.println("Enter a new last name: ");
                    clients[clientIndex].setLastName(scanner.next());
                    System.out.println("Last Name Updated to: " + clients[clientIndex].getLastName());
                }
                case 3 -> {
                    System.out.println("Enter a new email: ");
                    clients[clientIndex].setEmail(scanner.next());
                    System.out.println("Email Updated to: " + clients[clientIndex].getEmail());
                }
                case 4 -> running = false;
                default -> System.out.println("Invalid Input. Try Again");
            }
        }
    }

    public static void editTrip(Scanner scanner, Trip[] trips, int tripIndex, Client[] clients) {
        boolean running = true;
        while (running) {
            System.out.println("""
                        Which section do you want to edit?
                        1 - Destination
                        2 - Duration (in days)
                        3 - Base Price
                        4 - Client going on the trip
                        5 - Finished Editing
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
                    trips[tripIndex].setDurationInDays(scanner.nextInt());
                    System.out.println("Updated Duration to: " + trips[tripIndex].getDurationInDays() + " days.");
                }
                case 3 -> {
                    System.out.println("Enter a new base price: ");
                    trips[tripIndex].setBasePrice(scanner.nextInt());
                    System.out.println("Base Price Updated to: " + trips[tripIndex].getBasePrice());
                }
                case 4 -> {
                    showAll(clients, new Client());
                    System.out.println("Select the number of the new client going on the trip: ");
                    trips[tripIndex].setClientOnTrip(clients[scanner.nextInt()]);
                    System.out.println("Profile of the new client going on this trip: ");
                    System.out.println(trips[tripIndex].getClientOnTrip());
                }
                case 5 -> running = false;
                default -> System.out.println("Invalid Input. Try Again");
            }
        }
    }

    public static void testingScenario() {
        Client client1 = new Client("Sahon", "Shaha", "shahsahon@gmail.com");
        Client client2 = new Client("John", "Doe", "johndoe@gmail.com");
        Client client3 = new Client("Jane", "Doe", "janedoe@gmail.com");
        Client client4 = new Client(client3);

        Trip trip1 = new Trip("Japan", 7, 2000, client1);
        Trip trip2 = new Trip("France", 14, 3500, client2);
        Trip trip3 = new Trip("France", 14, 3500, client3);

        Bus bus1 = new Bus("CanadaTravels", "Montreal", "Quebec", "STM", 2);
        Bus bus2 = new Bus("AmericaUnited", "Toronto", "New York City", "TTC", 3);

        Train train1 = new Train("ViaRail Canada", "Montreal", "Vancouver City", "High Speed", "Business");
        Train train2 = new Train("North America Transit", "Boston", "Montreal", "Bullet Train", "Economy");

        Flight flight1 = new Flight("Europe Travel Agency", "Montreal", "France", "Air France Canada", 27);
        Flight flight2 = new Flight("Japan Airways", "Montreal", "Japan", "Air Canada", 31);

        Hotel hotel1 = new Hotel("Hilton Paris Opera", "France", 457, 5);
        Hotel hotel2 = new Hotel("Hotel Fine Sakai", "Japan", 37, 3);

        Hostel hostel1 = new Hostel("Marseille Deluxe", "France", 114, 4);
        Hostel hostel2 = new Hostel("Tokyo Yasashi", "Japan", 100, 2);

        Client[] clients = {client1, client2, client3, client4};
        Trip[] trips = {trip1, trip2, trip3};
        Bus[] buses = {bus1, bus2};
        Train[] trains = {train1, train2};
        Flight[] flights = {flight1, flight2};
        Hotel[] hotels = {hotel1, hotel2};
        Hostel[] hostels = {hostel1, hostel2};

        System.out.println("CLIENTS:");
        for (Client client : clients) System.out.println(client);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRIPS");
        for (Trip trip : trips) System.out.println(trip);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("BUSES");
        for (Bus bus : buses) System.out.println(bus);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("TRAINS");
        for (Train train : trains) System.out.println(train);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("FLIGHTS");
        for (Flight flight : flights) System.out.println(flight);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("HOTELS");
        for (Hotel hotel : hotels) System.out.println(hotel);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("HOSTELS");
        for (Hostel hostel : hostels) System.out.println(hostel);
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("TESTING EQUALS METHOD");
        System.out.println(train2.equals(hotel1));
        System.out.println(hostel1.equals(hostel2));
        System.out.println(client4.equals(client3));
        System.out.println("-----------------------------------------------------------------------------------------");

        // TODO 5,6,7
    }
}
