// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidAccommodationDataException;

public class Hostel extends Accommodation {
    private int sharedBeds;

    // Default Constructor
    public Hostel(){}

    // Parametrized Constructor
    public Hostel (String name, String location, int pricePerNight, int sharedBeds) throws InvalidAccommodationDataException {
        super(name, location, pricePerNight);
        if (pricePerNight > 150) {
            throw new InvalidAccommodationDataException("A hostel's price must be lower than 150$");
        }
        this.sharedBeds = sharedBeds;
    }

    // Copy Constructor
    public Hostel(Hostel hostel) {
        super(hostel);
        this.sharedBeds = hostel.getSharedBeds();
    }

    public String toString() {
        return super.toString() + "Amount of beds per room: " + this.sharedBeds + "\n";
    }

    public boolean equals(Hostel hostel) {
        // Ensuring the passed object is not null
        if (hostel == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (hostel.getClass() != this.getClass()) {
            return false;
        }

        if (this.getName().equals(hostel.getName()) && this.getLocation().equals(hostel.getLocation())
                && this.getPricePerNight() == hostel.getPricePerNight() && this.sharedBeds == hostel.getSharedBeds()) {
            return true;
        }
        else {
            return false;
        }
    }

    public double calculateCost() {
        // Discount of 10%
        return this.getPricePerNight() - (this.getPricePerNight() * 0.1);
    }

    public int getSharedBeds() {
        return sharedBeds;
    }

    public void setSharedBeds(int sharedBeds) {
        this.sharedBeds = sharedBeds;
    }
}
