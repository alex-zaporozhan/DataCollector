package ru.astondevs.data;

import ru.astondevs.model.DataClass;

import java.util.List;
import java.util.Map;

public class Student implements DataClass {

    enum Fields {
        GROUP_NUMBER("groupNumber"),
        AVERAGE_SCORE("averageScore"),
        RECORD_BOOK_NUMBER("recordBookNumber");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String groupNumber;
    private double averageScore;
    private String recordBookNumber;

    private Student(String groupNumber, double averageScore, String recordBookNumber) {
        this.groupNumber = groupNumber;
        this.averageScore = averageScore;
        this.recordBookNumber = recordBookNumber;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    //Билдер
    public static class Builder implements DataClass.Builder<Student>{
        private String groupNumber;
        private double averageScore;
        private String recordBookNumber;

        @Override
        public Builder addField(String field, String value) {
            switch (field) {
                case "groupNumber": groupNumber = value; break;
                case "averageScore": averageScore = Double.parseDouble(value); break;
                case "recordBookNumber": recordBookNumber = value; break;
            }
            return this;
        }

        public Builder addGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }
        public Builder addAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }
        public Builder addRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        @Override
        public Student build() {
            return new Student(groupNumber, averageScore, recordBookNumber);
        }
    }

    //Методы DataClass
    @Override
    public boolean isValid() {
        return groupNumber != null
                && recordBookNumber != null
                && averageScore >= 0 && averageScore <= 100;
    }

    @Override
    public Map<String, String> toCsvRecord() {
        return Map.of(
                Fields.GROUP_NUMBER.getName(), groupNumber,
                Fields.AVERAGE_SCORE.getName(), String.valueOf(averageScore),
                Fields.RECORD_BOOK_NUMBER.getName(), recordBookNumber
        );
    }

    @Override
    public String getField(String name) {
        return switch (name) {
            case "groupNumber" -> groupNumber;
            case "averageScore" -> String.valueOf(averageScore);
            case "recordBookNumber" -> recordBookNumber;
            default -> null;
        };
    }

    @Override
    public List<String> getFieldNames() {
        return List.of(
                Fields.GROUP_NUMBER.getName(),
                Fields.AVERAGE_SCORE.getName(),
                Fields.RECORD_BOOK_NUMBER.getName()
        );
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }
}

