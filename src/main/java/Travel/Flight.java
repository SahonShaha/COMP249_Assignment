// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

public class Flight extends Transportation {
    private String airlineName;
    private int luggageAllowance;

    // Default Constructor
    public Flight(){}

    // Parameterized Constructor
    public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, int luggageAllowance) {
        super(companyName, departureCity, arrivalCity);
        this.airlineName = airlineName;
        this.luggageAllowance = luggageAllowance;
    }

    // Copy Constructor
    public Flight(Flight flight) {
        super(flight);
        this.airlineName = flight.getAirlineName();
        this.luggageAllowance = flight.getLuggageAllowance();
    }

    public String toString() {
        return super.toString() + "Airline Company: " + this.airlineName + "\nLuggage Allowance (in kg): " + this.luggageAllowance  + "\n";
    }

    public boolean equals(Flight flight) {
        // Ensuring the passed object is not null
        if (flight == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (flight.getClass() != this.getClass()) {
            return false;
        }

        if (this.getCompanyName().equals(flight.getCompanyName()) && this.getDepartureCity().equals(flight.getDepartureCity())
                && this.getArrivalCity().equals(flight.getArrivalCity()) && this.airlineName.equals(flight.getAirlineName()) &&
                this.luggageAllowance == flight.getLuggageAllowance()) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getLuggageAllowance() {
        return luggageAllowance;
    }

    public void setLuggageAllowance(int luggageAllowance) {
        this.luggageAllowance = luggageAllowance;
    }
}
