package ru.astondevs.data;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class CarHelperTest {

    @Test
    public void testReadsRecordsFromInputStream() {
        CarHelper helper = new CarHelper();
        String csvData = "model;power;year\nToyota Camry;150;2020\nHonda Civic;120;2018";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        int result = helper.readRecords(inputStream);

        assertEquals(2, result);
    }

    @Test
    public void testWriteRecordsToOutputStream() {
        CarHelper helper = new CarHelper();
        String csvData = "model;power;year\nToyota Camry;150;2020\nHonda Civic;120;2018";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        helper.readRecords(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean result = helper.writeRecords(outputStream);

        assertTrue(result);
        String writtenData = outputStream.toString();
        String[] lines = writtenData.split("\n");

        assertEquals("model;power;year", lines[0]);

        assertTrue(writtenData.contains("Toyota Camry;150;2020"));
        assertTrue(writtenData.contains("Honda Civic;120;2018"));
    }
}
