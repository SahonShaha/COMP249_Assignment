// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

public class Bus extends Transportation {
    private String busCompany;
    private int stopsNum;
    private int baseFare;

    // Default Constructor
    public Bus() {};

    // Parametrized Constructor
    public Bus(String companyName, String departureCity, String arrivalCity, String busCompany, int stopsNum, int baseFare) {
        super(companyName, departureCity, arrivalCity);
        this.busCompany = busCompany;
        this.stopsNum = stopsNum;
        this.baseFare = baseFare;
    }

    // Copy Constructor
    public Bus(Bus bus) {
        super(bus);
        this.busCompany = bus.getBusCompany();
        this.stopsNum = bus.getStopsNum();
    }

    public String toString() {
        return super.toString() + "Bus Company: " + this.busCompany + "\nNumber of Stops: " + this.stopsNum + "\n";
    }

    public boolean equals(Bus bus) {
        // Ensuring the passed object is not null
        if (bus == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (bus.getClass() != this.getClass()) {
            return false;
        }

        if (this.getCompanyName().equals(bus.getCompanyName()) && this.getDepartureCity().equals(bus.getDepartureCity())
                && this.getArrivalCity().equals(bus.getArrivalCity()) && this.busCompany.equals(bus.getBusCompany()) &&
                this.stopsNum == bus.getStopsNum()) {
            return true;
        }
        else {
            return false;
        }
    }

    public double calculateCost() {
        // baseFare + a charge of 10cents per stop
        return baseFare + (stopsNum * 0.10);
    }

    public String getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) {
        this.busCompany = busCompany;
    }

    public int getStopsNum() {
        return stopsNum;
    }

    public void setStopsNum(int stopsNum) {
        this.stopsNum = stopsNum;
    }
}
