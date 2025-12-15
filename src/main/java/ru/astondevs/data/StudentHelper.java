package ru.astondevs.data;

import ru.astondevs.model.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentHelper implements Helper {

    private static final List<String> cols = List.of("groupNumber", "averageScore", "recordBookNumber");
    private List<Student> students = new ArrayList<>();
    private Comparator<Student> comparator = Comparator
            .comparing(Student::getGroupNumber)
            .thenComparing(Student::getAverageScore)
            .thenComparing(Student::getRecordBookNumber);

    @Override
    public String getExtention() {
        return "student.csv";
    }

    @Override
    public int readRecords(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return -1;
            }
            String[] headers = line.split(CSV_DELIMITER);
            int count = 0;

            String s;
            while ((s = reader.readLine()) != null) {
                String[] values = s.split(CSV_DELIMITER);

                if (values.length != headers.length) return -1;

                Student student = Student.newBuilder()
                        .addField(headers[0], values[0])
                        .addField(headers[1], values[1])
                        .addField(headers[2], values[2])
                        .build();

                if (student.isValid()) {
                    students.add(student);
                    count++;
                } else {
                    System.out.println("Invalid student record: " + s);
                }
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean writeRecords(OutputStream outputStream) {
        students.sort(comparator);

        try {
            boolean first = true;
            for (Student student : students) {
                if (first) {
                    outputStream.write(String.join(CSV_DELIMITER, cols).getBytes());
                    outputStream.write("\n".getBytes());
                    first = false;
                }
                Map<String, String> csvRecord = student.toCsvRecord();
                String row = cols.stream().map(csvRecord::get).collect(Collectors.joining(CSV_DELIMITER));
                outputStream.write(row.getBytes());
                outputStream.write("\n".getBytes());
            }
            students.clear();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setSorting(String field) {
        switch (field) {
            case "groupNumber":
                comparator = Comparator.comparing(Student::getGroupNumber)
                        .thenComparing(Student::getAverageScore)
                        .thenComparing(Student::getRecordBookNumber);
                break;
            case "averageScore":
                comparator = Comparator.comparing(Student::getAverageScore)
                        .thenComparing(Student::getGroupNumber)
                        .thenComparing(Student::getRecordBookNumber);
                break;
            case "recordBookNumber":
                comparator = Comparator.comparing(Student::getRecordBookNumber)
                        .thenComparing(Student::getGroupNumber)
                        .thenComparing(Student::getAverageScore);
                break;
            default:
                comparator = Comparator.comparing(Student::getGroupNumber)
                        .thenComparing(Student::getAverageScore)
                        .thenComparing(Student::getRecordBookNumber);
        }
    }
}
