// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidTransportDataException;
import Interfaces.CsvPersistable;
import Interfaces.Identifiable;

public abstract class Transportation implements Identifiable, CsvPersistable {
    private static int count = 3001; // Represents the amount of objects created. Will be used to create the ID
    private String transportationID;
    private String companyName;
    private String departureCity;
    private String arrivalCity;

    // Default Constructor
    public Transportation(){};

    // Parametrized Constructor
    public Transportation(String companyName, String departureCity, String arrivalCity) {
        this.transportationID = "TR" + count++;
        this.companyName = companyName;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
    }

    // Copy Constructor
    public Transportation(Transportation transportation) {
        this.transportationID = transportation.getId();
        this.companyName = transportation.getCompanyName();
        this.departureCity = transportation.getDepartureCity();
        this.arrivalCity = transportation.getArrivalCity();

    }

    public String toString() {
        return "Transportation ID: " + this.transportationID + "\nCompany Name: " + this.companyName
                + "\nCity of Departure: " + this.departureCity + "\nCity of Arrival: " + this.arrivalCity + "\n";
    }

    public boolean equals(Transportation transportation) {
        // Ensuring the passed object is not null
        if (transportation == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (transportation.getClass() != this.getClass()) {
            return false;
        }

        if (this.companyName.equals(transportation.getCompanyName()) && this.departureCity.equals(transportation.getDepartureCity())
                && this.arrivalCity.equals(transportation.getDepartureCity())) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Transportation fromCsvRow(String csvLine) throws InvalidTransportDataException {
        String[] fields = csvLine.split(";");

        if (fields.length != 7 && fields.length != 8) {
            throw new InvalidTransportDataException("Invalid Line Format");
        }

        return switch (fields[0]) {
            case "BUS"    -> Bus.fromCsvRow(csvLine);
            case "TRAIN"  -> Train.fromCsvRow(csvLine);
            case "FLIGHT" -> Flight.fromCsvRow(csvLine);
            default -> throw new InvalidTransportDataException("CSV line was neither a bus, train or flight");
        };
    }

    public abstract double calculateCost();

    public String getId() {
        return transportationID;
    }

    public void setTransportationID(String transportationID) {
        this.transportationID = transportationID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
}
