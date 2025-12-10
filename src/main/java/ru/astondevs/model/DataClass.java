package ru.astondevs.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataClass {
    interface Builder<T extends DataClass> {
        Builder<T> addField(String field, String value);
        T build();
    }
    boolean isValid();
    Map<String, String> toCsvRecord();
    String getField(String name);
    List<String> getFieldNames();
}