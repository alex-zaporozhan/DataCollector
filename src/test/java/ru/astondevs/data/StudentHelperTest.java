package ru.astondevs.data;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StudentHelperTest {

    @Test
    public void testRecordsFromInputStream() {
        StudentHelper helper = new StudentHelper();
        String csvData = "groupNumber;averageScore;recordBookNumber\n" +
                        "CS-101;85.5;RB001\n" +
                        "CS-102;92.0;RB002";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        int result = helper.readRecords(inputStream);
        assertEquals(2, result);
    }

    @Test
    public void testWriteRecordsToOutputStream() {
        StudentHelper helper = new StudentHelper();
        String csvData = "groupNumber;averageScore;recordBookNumber\n" +
                        "CS-101;85.5;RB001\n" +
                        "CS-102;92.0;RB002";

        helper.readRecords(new ByteArrayInputStream(csvData.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean result = helper.writeRecords(outputStream);
        assertTrue(result);

        String writtenData = outputStream.toString();

        assertEquals("groupNumber;averageScore;recordBookNumber",
                writtenData.split("\n")[0]);

        assertTrue(writtenData.contains("CS-101;85.5;RB001"));
        assertTrue(writtenData.contains("CS-102;92.0;RB002"));
    }
}
