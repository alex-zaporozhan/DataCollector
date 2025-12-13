package ru.astondevs.data;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;

public class BarrelHelperTest {

    @Test
    public void testReadsRecordsFromInputStream() {
        BarrelHelper helper = new BarrelHelper();
        String csvData = "Volume;stored material;made from\n12;water;wood\n25;jin;iron";
        java.io.InputStream inputStream = new java.io.ByteArrayInputStream(csvData.getBytes());

        int result = helper.readRecords(inputStream);

        assertEquals(result, 2);
    }

    @Test
    public void testWriteRecordsToOutputStream() {
        BarrelHelper helper = new BarrelHelper();
        String csvData = "Volume;stored material;made from\n12;water;tree\n25;jin;iron";
        java.io.InputStream inputStream = new java.io.ByteArrayInputStream(csvData.getBytes());
        helper.readRecords(inputStream);

        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        boolean result = helper.writeRecords(outputStream);

        assertTrue(result);
        String writtenData = outputStream.toString();
        assertEquals(writtenData.split("\n")[0], "volume;stored material;mage from");
        assertTrue(writtenData.contains("25;jin;iron"));
        assertTrue(writtenData.contains("12;water;wood"));
    }
}
