// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidTransportDataException;

public class Flight extends Transportation {
    private String airlineName;
    private int luggageAllowance;
    private int ticketPrice;

    // Default Constructor
    public Flight(){}

    // Parameterized Constructor
    public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, int luggageAllowance, int ticketPrice) throws InvalidTransportDataException {
        super(companyName, departureCity, arrivalCity);

        if (luggageAllowance < 0) {
            throw new InvalidTransportDataException("Luggage Allowance must be greater than 0.");
        }
        this.airlineName = airlineName;
        this.luggageAllowance = luggageAllowance;
        this.ticketPrice = ticketPrice;
    }

    // Copy Constructor
    public Flight(Flight flight) {
        super(flight);
        this.airlineName = flight.getAirlineName();
        this.luggageAllowance = flight.getLuggageAllowance();
        this.ticketPrice = flight.getTicketPrice();
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

    public String toCsvRow() {
        return "FLIGHT;" + super.getId() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity()
                + airlineName + ";" + luggageAllowance + ";" + ticketPrice;
    }

    public static Flight fromCsvRow(String csvLine) throws InvalidTransportDataException {
        String[] fields = csvLine.split(";");
        if (fields.length == 7) {
            Flight flight = new Flight(fields[2], fields[3], fields[4], "N/A", Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
            flight.setTransportationID(fields[1]);
            return flight;
        } else if (fields.length == 8) {
            Flight flight = new Flight(fields[2], fields[3], fields[4], fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]));
            flight.setTransportationID(fields[1]);
            return flight;
        } else {
            throw new InvalidTransportDataException("Invalid Line Format");
        }
    }

    public double calculateCost() {
        int luggageFee = 0;
        if (luggageAllowance > 50) {
            luggageFee = 50;
        }
        else if (luggageAllowance > 30) {
            luggageFee = 25;
        }

        return ticketPrice + luggageFee;
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

    public void setLuggageAllowance(int luggageAllowance) throws InvalidTransportDataException {
        if (luggageAllowance < 0) {
            throw new InvalidTransportDataException("Luggage Allowance must be greater than 0.");
        }
        this.luggageAllowance = luggageAllowance;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
