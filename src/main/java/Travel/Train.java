// ---------------------------------------------------------
// Assignment: 1
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Travel;

public class Train extends Transportation{
    private String trainType;
    private String seatClass;
    private int fare;

    // Default Constructor
    public Train() {super();}

    // Parametrized Constructor
    public Train(String companyName, String departureCity, String arrivalCity, String trainType, String seatClass, int fare) {
        super(companyName, departureCity, arrivalCity);
        this.trainType = trainType;
        this.seatClass = seatClass;
        this.fare = fare;
    }

    // Copy Constructor
    public Train(Train train) {
        super(train);
        this.trainType = train.getTrainType();
        this.seatClass = train.getSeatClass();
        this.fare = train.getFare();
    }

    public String toString() {
        return super.toString() + "Train Type: " + this.trainType + "\nSeat Class: " + this.seatClass + "\n";
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

        return seatPrice + fare;
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

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}
