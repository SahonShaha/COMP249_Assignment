// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Exceptions.InvalidTransportDataException;
import Travel.*;

import java.io.*;
import java.util.List;

public class TransportationFileManager {
    public static void saveTransportations(List<Transportation> transportations, int transportationsCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < transportationsCount; i++) {
            // First check if its a hotel or hostel
            if (transportations.get(i) instanceof Bus) {
                printWriter.println(
                        transportations.get(i).toCsvRow()
                );
            }
            else if (transportations.get(i) instanceof Train) {
                printWriter.println(
                        transportations.get(i).toCsvRow()
                );
            }
            else if (transportations.get(i) instanceof Flight) {
                printWriter.println(
                        transportations.get(i).toCsvRow()
                );
            }
        }
        printWriter.close();
    }

    public static int loadTransportations(List<Transportation> transportations, String filePath) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array
            count++;
            try {
                transportations.add(Transportation.fromCsvRow(currentLine));
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
