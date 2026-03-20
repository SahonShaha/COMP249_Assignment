// ---------------------------------------------------------
// Assignment: 2
// Question: 1
// Written by: Sahon Shaha 40339419
// ---------------------------------------------------------

package Persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
    final private static String fileName = "output/logs/errors.txt";
    private static int errorCount = 1;

    // Clears the error files
    public static void clear() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
            writer.close();
        } catch (IOException io) {
            System.err.println(io.getMessage());
        }
    }

    // Adds an error to the respective txt file
    public static void log(Exception e) throws IOException  {
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(errorCount + ": " + e);
        errorCount++;
        printWriter.close();
    }
}
