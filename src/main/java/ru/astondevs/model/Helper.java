package ru.astondevs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Helper {
    String CSV_DELIMITER = ";";
    String getExtention();
    int readRecords(InputStream inputStream);
    boolean writeRecords(OutputStream outputStream);
    void setSorting(String field);
}