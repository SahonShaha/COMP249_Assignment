// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

import Exceptions.InvalidTransportDataException;

public class Train extends Transportation{
    private String trainType;
    private String seatClass;
    private int baseFare;

    // Default Constructor
    public Train() {super();}

    // Parametrized Constructor
    public Train(String companyName, String departureCity, String arrivalCity, String trainType, String seatClass, int fare) {
        super(companyName, departureCity, arrivalCity);
        this.trainType = trainType;
        this.seatClass = seatClass;
        this.baseFare = fare;
    }

    // Copy Constructor
    public Train(Train train) {
        super(train);
        this.trainType = train.getTrainType();
        this.seatClass = train.getSeatClass();
        this.baseFare = train.getBaseFare();
    }

    public String toString() {
        return super.toString() + "Train Type: " + this.trainType + "\nSeat Class: " + this.seatClass +
                "\nBase Price: " + getBaseFare() + "\n";
    }

    public boolean equals(Train train) {
        // Ensuring the passed object is not null
        if (train == null) {
            return false;
        }

        // Ensuring the passed object is the same type
        if (train.getClass() != this.getClass()) {
            return false;
        }

        if (this.getCompanyName().equals(train.getCompanyName()) && this.getDepartureCity().equals(train.getDepartureCity())
                && this.getArrivalCity().equals(train.getArrivalCity()) && this.trainType.equals(train.getTrainType()) &&
            this.seatClass.equals(train.getSeatClass())) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toCsvRow() {
        return "TRAIN;" +  super.getId() + ";" + super.getCompanyName() + ";" + super.getDepartureCity() + ";" + super.getArrivalCity() + ";"
                + trainType + ";" + seatClass + ";" + baseFare;
    }

    public static Train fromCsvRow(String csvLine) throws InvalidTransportDataException {
        String[] fields = csvLine.split(";");
        if (fields.length == 7) {
            Train train = new Train(fields[2], fields[3], fields[4], fields[5], "N/A", Integer.parseInt(fields[6]));
            train.setTransportationID(fields[1]);
            return train;
        } else if (fields.length == 8) {
            Train train = new Train(fields[2], fields[3], fields[4], fields[5], fields[6], Integer.parseInt(fields[7]));
            train.setTransportationID(fields[1]);
            return train;
        } else {
            throw new InvalidTransportDataException("Invalid Line Format");
        }
    }

    public double calculateCost() {
        int seatPrice = 0;

        if (this.seatClass.equals("Cabin")) {
            seatPrice += 50;
        }
        else if (this.seatClass.equals("Deluxe")) {
            seatPrice += 100;
        }
        else {
            seatPrice += 0;
        }

        return seatPrice + baseFare;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public int getBaseFare() {
        return baseFare;
    }

    public void setFare(int fare) {
        this.baseFare = fare;
    }
}
