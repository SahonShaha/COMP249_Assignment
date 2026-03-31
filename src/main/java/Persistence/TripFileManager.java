// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Client.Client;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidClientDataException;
import Exceptions.InvalidTripDataException;
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
                trips.get(i).getId() + ";" +
                trips.get(i).getClientOnTrip().getId() + ";" +
                trips.get(i).getAccommodation().getId() + ";" +
                trips.get(i).getTransportation().getId() + ";" +
                trips.get(i).getDestination() + ";" +
                trips.get(i).getDurationInDays() + ";" +
                trips.get(i).getBasePrice()
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

            try {
                String[] fields = currentLine.split(";"); // Splits a line at every ';'

                if (fields.length != 7) { // If the split array has more than 5 elements
                    ErrorLogger.log(new Exception("Unable to read line " + (count + 1)));
                    continue; // We go to the next line
                }

                // Find Client, Accommodation and Transportation Objects
                int clientIndex = -1;
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getId().equals(fields[1])) {
                        clientIndex = i;
                        break;
                    }
                }

                int accommodationIndex = -1;
                for (int i = 0; i < accommodations.size(); i++) {
                    if (accommodations.get(i).getId().equals(fields[2])) {
                        accommodationIndex = i;
                        break;
                    }
                }

                int transportationIndex = -1;
                for (int i = 0; i < transportations.size(); i++) {
                    if (transportations.get(i).getId().equals(fields[3])) {
                        transportationIndex = i;
                        break;
                    }
                }

                try {
                    if (clientIndex == -1) {
                        throw new EntityNotFoundException("Client does not exist.");
                    }
                    if (transportationIndex == -1) {
                        throw new EntityNotFoundException("Transportation does not exist.");
                    }
                    if (accommodationIndex == -1) {
                        throw new EntityNotFoundException("Accommodation does not exist.");
                    }
                    trips.add(new Trip(fields[4], Integer.parseInt(fields[5]), Double.parseDouble(fields[6]), clients.get(clientIndex), transportations.get(transportationIndex), accommodations.get(accommodationIndex)));
                    trips.get(count).setTripID(fields[0]); // Since the parametrized constructor does not take an ID field
                    count++;
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException.getMessage());
                    ErrorLogger.log(entityNotFoundException);
                }
            }
            catch (InvalidTripDataException invalidTripDataException) {
                System.out.println(invalidTripDataException.getMessage());
                ErrorLogger.log(invalidTripDataException);
            }
        }

        bufferedReader.close();
        return count;
    }
}
