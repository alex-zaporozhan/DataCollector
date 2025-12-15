package ru.astondevs.data;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserHelperTest {
  @Test
  public void testReadsRecordsFromInputStream() {
    UserHelper helper = new UserHelper();
    String csvData = "name;age;password\nJohn;25;pass123\nJane;30;secret";
    java.io.InputStream inputStream = new java.io.ByteArrayInputStream(csvData.getBytes());

    int result = helper.readRecords(inputStream);

    assertEquals(result, 2);
  }

  @Test
  public void testWriteRecordsToOutputStream() {
    UserHelper helper = new UserHelper();
    String csvData = "name;password;age\nJohn;pass123;25\nJane;secret;30";
    java.io.InputStream inputStream = new java.io.ByteArrayInputStream(csvData.getBytes());
    helper.readRecords(inputStream);

    java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
    boolean result = helper.writeRecords(outputStream);

    assertTrue(result);
    String writtenData = outputStream.toString();
    assertEquals(writtenData.split("\n")[0], "name;password;age");
    assertTrue(writtenData.contains("Jane;secret;30"));
    assertTrue(writtenData.contains("John;pass123;25"));
  }
}