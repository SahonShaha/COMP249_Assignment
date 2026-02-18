// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

public abstract class Accommodation {
    private static int count = 4001; // Represents the amount of objects created. Will be used to create the ID
    private String accommodationID;
    private String name;
    private String location;
    private int pricePerNight;

    // Default Constructor
    public Accommodation() {}

    // Parametrized Constructor
    public Accommodation(String name, String location, int pricePerNight) {
        this.accommodationID = "A" + count++;
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
    }

    // Copy Constructor
    public Accommodation(Accommodation accommodation) {
        this.accommodationID = "A" + count++;
        this.name = accommodation.getName();
        this.location = accommodation.getLocation();
        this.pricePerNight = accommodation.getPricePerNight();
    }

    public String toString() {
        return "Accommodation ID: " + this.accommodationID +"\nName: " + this.name + "\nLocation: " + this.location
                + "\nPrice for a night: " + pricePerNight + "\n";
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

    abstract double calculateCost();

    public String getAccommodationID() {
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
}
