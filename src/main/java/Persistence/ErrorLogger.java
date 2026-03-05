package Persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {
    final private static String fileName = "output/logs/errors.txt";

    public static void log(Exception e) throws IOException  {
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(e);
        printWriter.close();
    }
}
