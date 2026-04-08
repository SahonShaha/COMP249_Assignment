// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Client.Client;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidClientDataException;
import Exceptions.InvalidTripDataException;
import Service.SmartTravelService;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.*;
import java.util.List;

public class TripFileManager {

    public static void saveTrip(List<Trip> trips, int tripCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < tripCount; i++) {
            printWriter.println(
                    trips.get(i).toCsvRow()
            );
        }

        printWriter.close();
    }

    public static int loadTrip(List<Trip> trips, String filePath, List<Client> clients, List<Accommodation> accommodations, List<Transportation> transportations) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array
            count++;
            try {
                trips.add(Trip.fromCsvRow(currentLine, clients, accommodations, transportations));
            }
            catch (InvalidTripDataException invalidTripDataException) {
                System.out.println(invalidTripDataException.getMessage());
                ErrorLogger.log(invalidTripDataException);
            }
            catch (EntityNotFoundException entityNotFoundException) {
                System.out.println(entityNotFoundException.getMessage());
            }
        }

        bufferedReader.close();
        return count;
    }
}
