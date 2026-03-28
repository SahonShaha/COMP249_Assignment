// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Exceptions.InvalidTransportDataException;
import Travel.*;

import java.io.*;

public class TransportationFileManager {
    public static void saveTransportations(Transportation[] transportations, int transportationsCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < transportationsCount; i++) {
            // First check if its a hotel or hostel
            if (transportations[i] instanceof Bus) {
                printWriter.println(
                        "BUS;" +
                        transportations[i].getId() + ";" +
                        transportations[i].getCompanyName() + ";" +
                        transportations[i].getDepartureCity() + ";" +
                        transportations[i].getArrivalCity() + ";" +
                        ((Bus) transportations[i]).getBusCompany() + ";" +
                        ((Bus) transportations[i]).getStopsNum() + ";" +
                        ((Bus) transportations[i]).getBaseFare()
                );
            }
            else if (transportations[i] instanceof Train) {
                printWriter.println(
                        "TRAIN;" +
                        transportations[i].getId() + ";" +
                        transportations[i].getCompanyName() + ";" +
                        transportations[i].getDepartureCity() + ";" +
                        transportations[i].getArrivalCity() + ";" +
                        ((Train) transportations[i]).getTrainType() + ";" +
                        ((Train) transportations[i]).getSeatClass() + ";" +
                        ((Train) transportations[i]).getFare()
                );
            }
            else if (transportations[i] instanceof Flight) {
                printWriter.println(
                        "FLIGHT;" +
                        transportations[i].getId() + ";" +
                        transportations[i].getCompanyName() + ";" +
                        transportations[i].getDepartureCity() + ";" +
                        transportations[i].getArrivalCity() + ";" +
                        ((Flight) transportations[i]).getAirlineName() + ";" +
                        ((Flight) transportations[i]).getLuggageAllowance() + ";" +
                        ((Flight) transportations[i]).getTicketPrice()
                );
            }
        }
        printWriter.close();
    }

    public static int loadTransportations(Transportation[] transportations, String filePath) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array

            try {
                String[] fields = currentLine.split(";"); // Splits a line at every ';'

                if (fields.length != 7 && fields.length != 8) { // If the split array has more than 7 elements
                    ErrorLogger.log(new Exception("Unable to read line " + (count + 1)));
                    continue; // We go to the next line
                }

                if (fields.length == 7) { // PDF Style
                    // Flight: Price + Luggage Allowance; airlineName = "N/A"
                    // Train: Fare + TrainType; SeatClass = "N/A"
                    // Bus: Price + StopsNum; busCompany = "N/A"
                    if (fields[0].equals("BUS")) {
                        transportations[count] = new Bus(fields[2], fields[3], fields[4], "N/A", Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    else if (fields[0].equals("TRAIN")) {
                        transportations[count] = new Train(fields[2], fields[3], fields[4], fields[5], "N/A", Integer.parseInt(fields[6]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    else if (fields[0].equals("FLIGHT")) {
                        transportations[count] = new Flight(fields[2], fields[3], fields[4], "N/A", Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    count++;
                }
                if (fields.length == 8) { // Full Loading
                    if (fields[0].equals("BUS")) {
                        transportations[count] = new Bus(fields[2], fields[3], fields[4], fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    else if (fields[0].equals("TRAIN")) {
                        transportations[count] = new Train(fields[2], fields[3], fields[4], fields[5], fields[6], Integer.parseInt(fields[7]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    else if (fields[0].equals("FLIGHT")) {
                        transportations[count] = new Flight(fields[2], fields[3], fields[4], fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]));
                        transportations[count].setTransportationID(fields[1]);
                    }
                    count++;
                }
            }
            catch (InvalidTransportDataException invalidAccommodationDataException) {
                System.out.println(invalidAccommodationDataException.getMessage());
                ErrorLogger.log(invalidAccommodationDataException);
            }
        }
        bufferedReader.close();

        return count;
    }
}
