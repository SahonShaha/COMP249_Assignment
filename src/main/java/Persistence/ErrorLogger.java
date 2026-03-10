package Persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
    final private static String fileName = "output/logs/errors.txt";
    private static int errorCount = 1;

    public static void log(Exception e) throws IOException  {
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(errorCount + " - " + e);
        errorCount++;
        printWriter.close();
    }
}
