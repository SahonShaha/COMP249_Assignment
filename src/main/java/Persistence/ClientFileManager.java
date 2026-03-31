// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import Client.Client;
import Exceptions.InvalidClientDataException;

import java.io.*;
import java.util.List;

public class ClientFileManager {
    public static void saveClients(List<Client> clients, int clientCount, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < clientCount; i++) {
            printWriter.println(
                clients.get(i).getId() + ";" +
                clients.get(i).getFirstName() + ";" +
                clients.get(i).getLastName() + ";" +
                clients.get(i).getEmail()
            );
        }
        printWriter.close();
    }

    public static int loadClients(List<Client> clients, String filePath) throws IOException {
        int count = 0;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        while ((currentLine = bufferedReader.readLine()) != null) { // .readLine iterates to the next line after it is called. So we must save that value in a variable.
            // Split the line into four parts and add it to an array
            // We create a new object with its fields being something from the array

            try {
                String[] fields = currentLine.split(";"); // Splits a line at every ';'

                if (fields.length != 4) { // If the split array has more than 5 elements
                    ErrorLogger.log(new Exception("Unable to read line " + (count + 1)));
                    continue; // We go to the next line
                }

                clients.add(new Client(fields[1], fields[2], fields[3]));
                clients.get(count).setClientID(fields[0]); // Since the parametrized constructor does not take an ID field
                count++;
            }
            catch (InvalidClientDataException invalidClientDataException) {
                System.out.println(invalidClientDataException.getMessage());
                ErrorLogger.log(invalidClientDataException);
            }
        }

        bufferedReader.close();
        return count;
    }
}
