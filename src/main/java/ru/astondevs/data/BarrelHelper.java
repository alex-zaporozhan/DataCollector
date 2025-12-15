package ru.astondevs.data;

import ru.astondevs.model.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class BarrelHelper implements Helper {

    private static final List<String> cols = List.of("volume","stored material","made from");
    private List<Barrel> barrels = new ArrayList<>();
    private Comparator comparator = Comparator.comparing(Barrel::getVolume).thenComparing(Barrel::getStoredMaterial).thenComparing(Barrel::getMadeFrom);

    @Override
    public String getExtention() {
        return "barrel.csv";
    }

    @Override
    public int readRecords(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            if (line.trim().isEmpty()) {
                System.out.println("File is empty");
                return -1;
            }
            String[] names = line.split(CSV_DELIMITER);
            if (names.length < 3) {
                System.out.println("File is broken");
                return -1;
            }

            int count = 0;
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                String[] values = s.split(CSV_DELIMITER);
                if (values.length != names.length) {
                    System.out.println("File is broken");
                    return -1;
                }
                Barrel barrel = Barrel.newBuilder()
                        .addField(names[0], values[0])
                        .addField(names[1], values[1])
                        .addField(names[2], values[2])
                        .build();
                barrels.add(barrel);
                count++;
            }
            return count;
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public boolean writeRecords(OutputStream outputStream) {
        barrels.sort(comparator);
        try {
            boolean first = true;
            for (Barrel barrel : barrels) {
                if (first) {
                    outputStream.write(join(CSV_DELIMITER,cols).getBytes());
                    outputStream.write("\n".getBytes());
                }
                first = false;
                final Map<String, String> csvRecord = barrel.toCsvRecord();
                String row = cols.stream().map(csvRecord::get).collect(Collectors.joining(CSV_DELIMITER));
                outputStream.write(row.getBytes());
                outputStream.write("\n".getBytes());
            }
            barrels.clear();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void setSorting(String field) {
        switch (field) {
            case "made from":
                comparator = Comparator.comparing(Barrel::getMadeFrom).thenComparing(Barrel::getVolume).thenComparing(Barrel::getStoredMaterial);
                break;
            case "volumee":
                comparator = Comparator.comparing(Barrel::getVolume).thenComparing(Barrel::getMadeFrom).thenComparing(Barrel::getStoredMaterial);
                break;
            case "stored material":
                comparator = Comparator.comparing(Barrel::getStoredMaterial).thenComparing(Barrel::getMadeFrom).thenComparing(Barrel::getVolume);
                break;
            case "special":
                comparator = Comparator.naturalOrder(); // TODO Fix
                break;
        }
    }
}