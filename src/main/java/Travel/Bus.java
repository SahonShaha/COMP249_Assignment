// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidTransportDataException;

public class Bus extends Transportation {
    private String busCompany;
    private int stopsNum;
    private int baseFare;

    // Default Constructor
    public Bus() {};

    // Parametrized Constructor
    public Bus(String companyName, String departureCity, String arrivalCity, String busCompany, int stopsNum, int baseFare) throws InvalidTransportDataException{
        super(companyName, departureCity, arrivalCity);
        if (stopsNum < 1) {
            throw new InvalidTransportDataException("A Bus must have at least 1 stop.");
        }
        this.busCompany = busCompany;
        this.stopsNum = stopsNum;
        this.baseFare = baseFare;
    }

    // Copy Constructor
    public Bus(Bus bus) throws InvalidTransportDataException {
        super(bus);
        if (bus.getStopsNum() < 1) {
            throw new InvalidTransportDataException("A Bus must have at least 1 stop.");
        }
        this.busCompany = bus.getBusCompany();
        this.stopsNum = bus.getStopsNum();
        this.baseFare = bus.getBaseFare();
    }

    public String toString() {
        return super.toString() + "Bus Company: " + this.busCompany + "\nNumber of Stops: " + this.stopsNum +
                "\nBase Price: " + getBaseFare() + "\n";
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

    public String toCsvRow() {
        return "BUS;" + super.getId() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity()
        + ";" + busCompany + ";" + stopsNum + ";" + baseFare;
    }

    public static Bus fromCsvRow(String csvLine) throws InvalidTransportDataException {
        String[] fields = csvLine.split(";");
        if (fields.length == 7) {
            Bus bus = new Bus(fields[2], fields[3], fields[4], "N/A", Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
            bus.setTransportationID(fields[1]);
            return bus;
        } else if (fields.length == 8) {
            Bus bus = new Bus(fields[2], fields[3], fields[4], fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]));
            bus.setTransportationID(fields[1]);
            return bus;
        } else {
            throw new InvalidTransportDataException("Invalid Line Format");
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

    public void setStopsNum(int stopsNum) throws InvalidTransportDataException {
        if (stopsNum < 1) {
            throw new InvalidTransportDataException("A Bus must have at least 1 stop.");
        }
        this.stopsNum = stopsNum;
    }

    public void setBaseFare(int baseFare) {
        this.baseFare = baseFare;
    }

    public int getBaseFare() {
        return baseFare;
    }
}
