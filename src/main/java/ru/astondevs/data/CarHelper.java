package ru.astondevs.data;

import ru.astondevs.model.Helper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static ru.astondevs.data.Car.Fields.*;

public class CarHelper implements Helper {

    private static final List<String> cols = List.of(MODEL.getName(), POWER.getName(), YEAR.getName());
    private final List<Car> cars = new ArrayList<>();
    private Comparator comparator = Comparator
            .comparing(Car::getModel)
            .thenComparing(Car::getPower)
            .thenComparing(Car::getYear);

    @Override
    public String getExtention() {
        return "car.csv";
    }

    @Override
    public int readRecords(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                System.out.println("File is empty");
                return -1;
            }

            String[] headers = line.split(CSV_DELIMITER);
            if (headers.length < 3) {
                System.out.println("File is broken");
                return -1;
            }

            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(CSV_DELIMITER);
                if (values.length != headers.length) {
                    System.out.println("File is broken");
                    return -1;
                }

                Car car = Car.newBuilder()
                        .addField(headers[0], values[0])
                        .addField(headers[1], values[1])
                        .addField(headers[2], values[2])
                        .build();
                cars.add(car);
                count++;
            }
            return count;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean writeRecords(OutputStream outputStream) {
        cars.sort(comparator);
        try {
            outputStream.write(String.join(CSV_DELIMITER, cols).getBytes());
            outputStream.write("\n".getBytes());

            for (Car car : cars) {
                Map<String, String> csvRecord = car.toCsvRecord();
                String row = cols.stream()
                        .map(csvRecord::get)
                        .collect(Collectors.joining(CSV_DELIMITER));
                outputStream.write(row.getBytes());
                outputStream.write("\n".getBytes());
            }

            cars.clear();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void setSorting(String field) {
        switch (field) {
            case "model":
                comparator = Comparator.comparing(Car::getModel)
                        .thenComparing(Car::getPower)
                        .thenComparing(Car::getYear);
                break;
            case "power":
                comparator = Comparator.comparing(Car::getPower)
                        .thenComparing(Car::getModel)
                        .thenComparing(Car::getYear);
                break;
            case "year":
                comparator = Comparator.comparing(Car::getYear)
                        .thenComparing(Car::getModel)
                        .thenComparing(Car::getPower);
                break;
            case "special":
                comparator = Comparator.naturalOrder();
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }
}
