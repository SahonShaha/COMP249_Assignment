// ---------------------------------------------------------
// Assignment: 3
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Exceptions.InvalidAccommodationDataException;
import Travel.Accommodation;
import Travel.Hostel;
import Travel.Hotel;

import java.io.*;
import java.util.List;

public class AccommodationFileManager {
    public static void saveAccommodations(List<Accommodation> accommodations, int accommodationCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < accommodationCount; i++) {
            // First check if its a hotel or hostel
            if (accommodations.get(i) instanceof Hotel) {
                printWriter.println(
                        accommodations.get(i).toCsvRow()
                );
            }
            else if (accommodations.get(i) instanceof Hostel) {
                printWriter.println(
                        accommodations.get(i).toCsvRow()
                );
            }
        }
        printWriter.close();
    }

    public static int loadAccommodations(List<Accommodation> accommodations, String filePath) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array
            count++;
            try {
                accommodations.add(Accommodation.fromCsvRow(currentLine));
            }
            catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                System.out.println(invalidAccommodationDataException.getMessage());
                ErrorLogger.log(invalidAccommodationDataException);
            }
        }
        bufferedReader.close();
        return count;
    }
}
