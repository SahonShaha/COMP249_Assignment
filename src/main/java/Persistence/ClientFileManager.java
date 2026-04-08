// ---------------------------------------------------------
// Assignment: 3
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
            printWriter.println(clients.get(i).toCsvRow());
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
            count++;
            try {clients.add(Client.fromCsvRow(currentLine));}
            catch (InvalidClientDataException invalidClientDataException) {
                System.out.println(invalidClientDataException.getMessage());
                ErrorLogger.log(invalidClientDataException);
            }
        }

        bufferedReader.close();
        return count;
    }
}
