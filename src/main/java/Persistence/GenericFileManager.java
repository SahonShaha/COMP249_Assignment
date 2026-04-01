package Persistence;

import Client.Client;
import Exceptions.EntityNotFoundException;
import Interfaces.CsvPersistable;
import Service.SmartTravelService;
import Travel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericFileManager {
    public static <T extends CsvPersistable> List<T> load(String filepath, Class<T> clazz) throws IOException {
        List<T> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        int lineNumber = 0;

        while ((currentLine = bufferedReader.readLine()) != null) {
            lineNumber++;
            try {
                T item = parseLine(currentLine, clazz);
                list.add(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                ErrorLogger.log(new EntityNotFoundException("Unable to read line " + lineNumber));
            }
        }

        bufferedReader.close();
        return list;
    }

    public static <T extends CsvPersistable> void save(List<T> items, String filepath) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filepath));

        for (T item : items) {
            printWriter.println(item.toCsvRow());
        }

        printWriter.close();
    }

    private static <T extends CsvPersistable> T parseLine(String csvLine, Class<T> clazz) throws Exception {
        return (T) switch (clazz.getSimpleName()) {
            case "Client" -> Client.fromCsvRow(csvLine);
            case "Hotel" -> Hotel.fromCsvRow(csvLine);
            case "Hostel" -> Hostel.fromCsvRow(csvLine);
            case "Bus" -> Bus.fromCsvRow(csvLine);
            case "Train" -> Train.fromCsvRow(csvLine);
            case "Flight" -> Flight.fromCsvRow(csvLine);
            case "Trip" -> Trip.fromCsvRow(csvLine, SmartTravelService.getClients(), SmartTravelService.getAccommodations(), SmartTravelService.getTransportations());
            case "Accommodation" -> Accommodation.fromCsvRow(csvLine);
            case "Transportation" -> Transportation.fromCsvRow(csvLine);
            default -> throw new EntityNotFoundException("Unknown class: " + clazz.getSimpleName());
        };
    }
}
