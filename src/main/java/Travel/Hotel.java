// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

public class Hotel extends Accommodation {
    private int stars;

    // Default Constructor
    public Hotel(){}

    // Parametrized Constructor
    public Hotel(String name, String location, int pricePerNight, int stars) {
        super(name, location, pricePerNight);
        this.stars = stars;
    }

    // Copy Constructor
    public Hotel(Hotel hotel) {
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

    public double calculateCost(int numberOfDays) {
        return this.getPricePerNight() * numberOfDays;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
