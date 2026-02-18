// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Client.Client;

public class Trip {
    private static int count = 2001; // Represents the amount of objects created. Will be used to create the ID
    private String tripID;
    private String destination;
    private int durationInDays;
    private int basePrice;
    private Client clientOnTrip;
    private Transportation transportation;
    private Accommodation accommodation;

    // Default Constructor
    public Trip() {}

    // Parametrized Constructor
    public Trip(String destination, int durationInDays, int basePrice, Client client, Transportation transportation, Accommodation accommodation) {
        this.tripID = "T" + count++;
        this.destination = destination;
        this.durationInDays = durationInDays;
        this.basePrice = basePrice;
        this.clientOnTrip = new Client(client);

        // Creating Deep Copy of Transportation
        // Typecasting to the object here will not cause a problem because we are verifying the type during runtime
        if (transportation instanceof Bus) {
            this.transportation = new Bus((Bus) transportation);
        }
        else if (transportation instanceof Flight) {
            this.transportation = new Flight((Flight) transportation);
        }
        else if (transportation instanceof Train) {
            this.transportation = new Train((Train) transportation);
        }
        else {
            this.transportation = null;
        }

        // Creating Deep Copy of Accommodation
        if (accommodation instanceof Hotel) {
            this.accommodation = new Hotel((Hotel) accommodation);
        }
        else if (accommodation instanceof Hostel) {
            this.accommodation = new Hostel((Hostel) accommodation);
        }
        else {
            this.accommodation = null;
        }
    }

    // Copy Constructor
    public Trip(Trip trip) {
        this.tripID = "T" + count++;
        this.destination = trip.getDestination();
        this.durationInDays = trip.getDurationInDays();
        this.basePrice = trip.getBasePrice();
        this.clientOnTrip = new Client(trip.getClientOnTrip()); // Creates a Deep Copy of Client
    }

    public String toString() {
        return "Trip ID: " + this.tripID + "\nDestination: " + this.destination + "\nTrip Duration: " + durationInDays + " Days"
                + "\nBase Price: " + this.basePrice + "$"
                + "\nClient on Trip: " + this.clientOnTrip.getFirstName() + " " + this.clientOnTrip.getLastName() + "\n";
    }

    public boolean equals(Trip trip) {
        // Ensuring the passed object is not null
        if (trip == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (trip.getClass() != this.getClass()) {
            return false;
        }

        if (this.destination.equals(trip.getDestination()) && this.durationInDays == trip.getDurationInDays()
                && this.basePrice == trip.getBasePrice() && this.clientOnTrip.equals(trip.getClientOnTrip())) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public Client getClientOnTrip() {
        return clientOnTrip;
    }

    public void setClientOnTrip(Client clientOnTrip) {
        this.clientOnTrip = clientOnTrip;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
