// ---------------------------------------------------------
// Assignment: 1
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
    public Hotel(String name, String location, int pricePerNight, int stars) throws InvalidAccommodationDataException {
        super(name, location, pricePerNight);

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

    public double calculateCost() {
        // Service Fee of 5$ per night
        return this.getPricePerNight() + 5;
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
