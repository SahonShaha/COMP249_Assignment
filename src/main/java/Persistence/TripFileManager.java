package Persistence;

import Client.Client;
import Exceptions.EntityNotFoundException;
import Exceptions.InvalidClientDataException;
import Exceptions.InvalidTripDataException;
import Travel.Accommodation;
import Travel.Transportation;
import Travel.Trip;

import java.io.*;

public class TripFileManager {

    public static void saveTrip(Trip[] trips,int tripCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < tripCount; i++) {
            printWriter.println(
                trips[i].getTripID() + ";" +
                trips[i].getClientOnTrip().getClientID() + ";" +
                trips[i].getAccommodation().getAccommodationID() + ";" +
                trips[i].getTransportation().getTransportationID() + ";" +
                trips[i].getDestination() + ";" +
                trips[i].getDurationInDays() + ";" +
                trips[i].getBasePrice()
            );
        }

        printWriter.close();
    }

    // FIXME TRIPS NOT LOADING, STUCK AT CLIENTS
    public static int loadTrip(Trip[] trips, String filePath, Client[] clients, Accommodation[] accommodations, Transportation[] transportations) throws IOException {
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
                for (int i = 0; i < clients.length; i++) {
                    if (clients[i] == null) { // Once it hits null, that means all available elements have been printed
                        break;
                    }

                    if (clients[i].getClientID().equals(fields[1])) {
                        clientIndex = i;
                        break;
                    }
                }

                int accommodationIndex = -1;
                for (int i = 0; i < accommodations.length; i++) {
                    if (accommodations[i] == null) { // Once it hits null, that means all available elements have been printed
                        break;
                    }

                    if (accommodations[i].getAccommodationID().equals(fields[2])) {
                        accommodationIndex = i;
                        break;
                    }
                }

                int transportationIndex = -1;
                for (int i = 0; i < transportations.length; i++) {
                    if (transportations[i] == null) { // Once it hits null, that means all available elements have been printed
                        break;
                    }

                    if (transportations[i].getTransportationID().equals(fields[3])) {
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
                    trips[count] = new Trip(fields[4], Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), clients[clientIndex], transportations[transportationIndex], accommodations[accommodationIndex]);
                    trips[count].setTripID(fields[0]); // Since the parametrized constructor does not take an ID field
                    count++;
                }
                catch (EntityNotFoundException entityNotFoundException) {
                    System.out.println(entityNotFoundException);
                    ErrorLogger.log(entityNotFoundException);
                }
            }
            catch (InvalidTripDataException invalidTripDataException) {
                System.out.println(invalidTripDataException);
                ErrorLogger.log(invalidTripDataException);
            }
        }

        bufferedReader.close();
        return count;
    }
}
