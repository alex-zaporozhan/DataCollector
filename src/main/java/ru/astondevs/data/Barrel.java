package ru.astondevs.data;

import ru.astondevs.model.DataClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.astondevs.data.Barrel.Fields.*;

public class Barrel implements DataClass {

    enum Fields {
        VOLUME("volume"),
        STORED_MATERIAL("stored material"),
        MADE_FROM("made from");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Integer volume;
    private String storedMaterial;
    private String madeFrom;

    static class Builder implements DataClass.Builder<Barrel> {
        private int volume;
        private String storedMaterial;
        private String madeFrom;

        @Override
        public Builder addField(String field, String value) {
            switch (field) {
                case "volume":
                    volume = Integer.parseInt(value);
                    break;
                case "password":
                    storedMaterial = value;
                    break;
                case "age":
                    madeFrom = value;
                    break;
            }
            return this;
        }

        public Builder addName(Integer volume) {
            this.volume = volume;
            return this;
        }
        public Builder addPassword(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }
        public Builder addAge(String madeFrom) {
            this.madeFrom = madeFrom;
            return this;
        }

        @Override
        public Barrel build() {
            return new Barrel(volume, storedMaterial, madeFrom);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private Barrel(Integer volume, String password, String madeFrom) {
        this.volume = volume;
        this.storedMaterial = password;
        this.madeFrom = madeFrom;
    }

    @Override
    public boolean isValid() {
        return volume != null && storedMaterial != null && madeFrom != null;
    }

    @Override
    public Map<String, String> toCsvRecord() {
        return Map.of(VOLUME.name, volume.toString(), STORED_MATERIAL.name, storedMaterial, MADE_FROM.name, madeFrom);
    }

    @Override
    public String getField(String name) {
        return switch (name) {
            case "volume" -> volume.toString();
            case "storedMaterial" -> storedMaterial;
            case "age" -> madeFrom;
            default -> null;
        };
    }

    public Integer getVolume() {
        return volume;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public String getMadeFrom() {
        return madeFrom;
    }

    @Override
    public List<String> getFieldNames() {
        return List.of(VOLUME.name, STORED_MATERIAL.name, MADE_FROM.name);
    }
}