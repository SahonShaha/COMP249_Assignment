// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidAccommodationDataException;

public class Hotel extends Accommodation {
    private int stars;

    // Default Constructor
    public Hotel(){}

    // Parametrized Constructor
    public Hotel(String name, String location, int pricePerNight, int numberOfNights, int stars) throws InvalidAccommodationDataException {
        super(name, location, pricePerNight, numberOfNights);

        if (stars < 0 || stars > 5) {
            throw new InvalidAccommodationDataException("Stars must be between 1 and 5.");
        }
        this.stars = stars;
    }

    // Copy Constructor
    public Hotel(Hotel hotel) throws InvalidAccommodationDataException {
        super(hotel);
        this.stars = hotel.getStars();
    }

    public String toString() {
        return super.toString() + "Stars: " + this.stars + "\n";
    }

    public boolean equals(Hotel hotel) {
        // Ensuring the passed object is not null
        if (hotel == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (hotel.getClass() != this.getClass()) {
            return false;
        }

        if (this.getName().equals(hotel.getName()) && this.getLocation().equals(hotel.getLocation())
                && this.getPricePerNight() == hotel.getPricePerNight() && this.stars == hotel.getStars()) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toCsvRow() {
        return "HOTEL;" + super.getId() + ";" + super.getName() + ";" + super.getLocation() + ";" + super.getPricePerNight() +
                ";" + super.getNumberOfNights() + ";" + stars;
    }

    public static Hotel fromCsvRow(String csvLine) throws InvalidAccommodationDataException {
        String[] fields = csvLine.split(";");

        if (fields.length == 6) {
            Hotel hotel = new Hotel(fields[2], fields[3], Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), 0);
            hotel.setAccommodationID(fields[1]);
            return hotel;
        } else if (fields.length == 7) {
            Hotel hotel = new Hotel(fields[2], fields[3], Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
            hotel.setAccommodationID(fields[1]);
            return hotel;
        } else {
            throw new InvalidAccommodationDataException("Invalid Line Format");
        }
    }

    public double calculateCost() {
        // Service Fee of 5$ per night
        return ((this.getPricePerNight() * this.getNumberOfNights()) + (this.getNumberOfNights() * 5));
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) throws InvalidAccommodationDataException{
        if (stars < 0 || stars > 5) {
            throw new InvalidAccommodationDataException("Stars must be between 1 and 5.");
        }
        this.stars = stars;
    }
}
