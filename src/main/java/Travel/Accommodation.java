// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidAccommodationDataException;
import Interfaces.CsvPersistable;
import Interfaces.Identifiable;

public abstract class Accommodation implements Identifiable, CsvPersistable {
    private static int count = 4001; // Represents the amount of objects created. Will be used to create the ID
    private String accommodationID;
    private String name;
    private String location;
    private int pricePerNight;
    private int numberOfNights;

    // Default Constructor
    public Accommodation() {}

    // Parametrized Constructor
    public Accommodation(String name, String location, int pricePerNight, int numberOfNights) throws InvalidAccommodationDataException {
        this.accommodationID = "A" + count++;

        if (pricePerNight < 1) {
            throw new InvalidAccommodationDataException("The price per night needs to be greater than 0.");
        }
        if (numberOfNights < 1) {
            throw new InvalidAccommodationDataException("You must book an accommodation for at least 1 night.");
        }
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.numberOfNights = numberOfNights;
    }

    // Copy Constructor
    public Accommodation(Accommodation accommodation) throws InvalidAccommodationDataException{
        this.accommodationID = accommodation.getId();
        if (accommodation.getPricePerNight() < 1) {
            throw new InvalidAccommodationDataException("The price per night needs to be greater than 0.");
        }
        if (accommodation.getNumberOfNights() < 1) {
            throw new InvalidAccommodationDataException("You must book an accommodation for at least 1 night.");
        }
        this.name = accommodation.getName();
        this.location = accommodation.getLocation();
        this.pricePerNight = accommodation.getPricePerNight();
        this.numberOfNights = accommodation.getNumberOfNights();
    }

    public String toString() {
        return "Accommodation ID: " + this.accommodationID +"\nName: " + this.name + "\nLocation: " + this.location
                + "\nPrice for a night: " + pricePerNight + "\nDuration (in night(s)): " + numberOfNights + "\n";
    }

    public boolean equals(Accommodation accommodation) {
        // Ensuring the passed object is not null
        if (accommodation == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (accommodation.getClass() != this.getClass()) {
            return false;
        }

        if (this.name.equals(accommodation.getName()) && this.location.equals(accommodation.getLocation())
                && this.pricePerNight == accommodation.getPricePerNight()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Accommodation fromCsvRow(String csvLine) throws InvalidAccommodationDataException {
        String[] fields = csvLine.split(";");

        if (fields.length != 6 && fields.length != 7) { // Verification
            throw new InvalidAccommodationDataException("Invalid Line Format");
        }

        return switch (fields[0]) { // Checks whether its hotel or hostel and calls that method
            case "HOTEL"  -> Hotel.fromCsvRow(csvLine);
            case "HOSTEL" -> Hostel.fromCsvRow(csvLine);
            default -> throw new InvalidAccommodationDataException("CSV line was neither a hotel or hostel");
        };
    }

    public abstract double calculateCost();

    public String getId() {
        return accommodationID;
    }

    public void setAccommodationID(String accommodationID) {
        this.accommodationID = accommodationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }
}
