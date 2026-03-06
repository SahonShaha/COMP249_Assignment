package Persistence;

import Exceptions.InvalidAccommodationDataException;
import Travel.Accommodation;
import Travel.Hostel;
import Travel.Hotel;

import java.io.*;

public class AccommodationFileManager {
    public static void saveAccommodations(Accommodation[] accommodations, int accommodationCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < accommodationCount; i++) {
            // First check if its a hotel or hostel
            if (accommodations[i] instanceof Hotel) {
                printWriter.println(
                    "HOTEL;" +
                    accommodations[i].getAccommodationID() + ";" +
                    accommodations[i].getName() + ";" +
                    accommodations[i].getLocation() + ";" +
                    accommodations[i].getPricePerNight() + ";" +
                    ((Hotel) accommodations[i]).getStars()
                );
            }
            else if (accommodations[i] instanceof Hostel) {
                printWriter.println(
                    "HOSTEL;" +
                    accommodations[i].getAccommodationID() + ";" +
                    accommodations[i].getName() + ";" +
                    accommodations[i].getLocation() + ";" +
                    accommodations[i].getPricePerNight() + ";" +
                    ((Hostel) accommodations[i]).getSharedBeds()
                );
            }
        }
        printWriter.close();
    }

    public static int loadAccommodations(Accommodation[] accommodations, String filePath) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array

            try {
                String[] fields = currentLine.split(";"); // Splits a line at every ';'

                if (fields.length != 6) { // If the split array has more than 5 elements
                    ErrorLogger.log(new Exception("Unable to read line " + (count + 1)));
                    continue; // We go to the next line
                }

                if (fields[0].equals("HOTEL")) {
                    accommodations[count] = new Hotel(fields[2], fields[3], Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));
                    accommodations[count].setAccommodationID(fields[1]);
                }
                else if (fields[0].equals("HOSTEL")) {
                    accommodations[count] = new Hostel(fields[2], fields[3], Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));
                    accommodations[count].setAccommodationID(fields[1]);
                }
                count++;
            }
            catch (InvalidAccommodationDataException invalidAccommodationDataException) {
                System.out.println(invalidAccommodationDataException);
                ErrorLogger.log(invalidAccommodationDataException);
            }
        }
        bufferedReader.close();
        return count;
    }
}
