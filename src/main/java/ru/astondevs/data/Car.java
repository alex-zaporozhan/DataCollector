package ru.astondevs.data;

import ru.astondevs.model.DataClass;
import java.util.List;
import java.util.Map;

import static ru.astondevs.data.Car.Fields.*;

public class Car implements DataClass {

    public enum Fields {
        MODEL("model"),
        POWER("power"),
        YEAR("year");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String model;
    private Integer power;
    private Integer year;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Car(String model, Integer power, Integer year) {
        this.model = model;
        this.power = power;
        this.year = year;
    }

    @Override
    public boolean isValid() {
        return model != null && !model.trim().isEmpty() &&
                power != null && power > 0 &&
                year != null && year > 1900 && year <= java.time.Year.now().getValue();
    }

    @Override
    public Map<String, String> toCsvRecord() {
        return Map.of(
                MODEL.name, model,
                POWER.name, power.toString(),
                YEAR.name, year.toString()
        );
    }

    @Override
    public String getField(String name) {
        return switch (name) {
            case "model" -> model;
            case "power" -> power.toString();
            case "year" -> year.toString();
            default -> null;
        };
    }

    // Геттеры
    public String getModel() {
        return model;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public List<String> getFieldNames() {
        return List.of(MODEL.name, POWER.name, YEAR.name);
    }

    public static class Builder implements DataClass.Builder<Car> {
        private String model;
        private Integer power;
        private Integer year;

        @Override
        public Builder addField(String field, String value) {
            switch (field) {
                case "model":
                    model = value;
                    break;
                case "power":
                    power = Integer.parseInt(value);
                    break;
                case "year":
                    year = Integer.parseInt(value);
                    break;
            }
            return this;
        }

        public Builder addModel(String model) {
            this.model = model;
            return this;
        }

        public Builder addPower(Integer power) {
            this.power = power;
            return this;
        }

        public Builder addYear(Integer year) {
            this.year = year;
            return this;
        }

        @Override
        public Car build() {
            return new Car(model, power, year);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", power=" + power +
                ", year=" + year +
                '}';
    }
}
