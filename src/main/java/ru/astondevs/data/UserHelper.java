package ru.astondevs.data;

import org.w3c.dom.ls.LSOutput;
import ru.astondevs.model.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class UserHelper implements Helper {

  private static final List<String> cols = List.of("name","password","age");
  private List<User> users = new ArrayList<>();
  private Comparator comparator = Comparator.comparing(User::getName).thenComparing(User::getAge).thenComparing(User::getPassword);

  @Override
  public String getExtention() {
    return "user.csv";
  }

  @Override
  public int readRecords(InputStream inputStream) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line = reader.readLine();
      if (line.trim().isEmpty()) {
        System.out.println("File is empty");
        return -1;
      }
      String[] names = line.split(CSV_DELIMITER);
      if (names.length < 3) {
        System.out.println("File is broken");
        return -1;
      }

      int count = 0;
      while (true) {
        String s = reader.readLine();
        if (s == null) {
          break;
        }
        String[] values = s.split(CSV_DELIMITER);
        if (values.length != names.length) {
          System.out.println("File is broken");
          return -1;
        }
        User user = User.newBuilder()
            .addField(names[0], values[0])
            .addField(names[1], values[1])
            .addField(names[2], values[2])
            .build();
        users.add(user);
        count++;
      }
      return count;
    } catch (IOException e) {
      return 0;
    }
  }

  @Override
  public boolean writeRecords(OutputStream outputStream) {
    users.sort(comparator);
    try {
      boolean first = true;
      for (User user : users) {
        if (first) {
          outputStream.write(join(CSV_DELIMITER,cols).getBytes());
          outputStream.write("\n".getBytes());
        }
        first = false;
        final Map<String, String> csvRecord = user.toCsvRecord();
        String row = cols.stream().map(csvRecord::get).collect(Collectors.joining(CSV_DELIMITER));
        outputStream.write(row.getBytes());
        outputStream.write("\n".getBytes());
      }
      users.clear();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public void setSorting(String field) {
    switch (field) {
      case "age":
        comparator = Comparator.comparing(User::getAge).thenComparing(User::getName).thenComparing(User::getPassword);
        break;
      case "name":
        comparator = Comparator.comparing(User::getName).thenComparing(User::getAge).thenComparing(User::getPassword);
        break;
      case "password":
        comparator = Comparator.comparing(User::getPassword).thenComparing(User::getAge).thenComparing(User::getName);
        break;
      case "special":
        comparator = Comparator.naturalOrder();
        break;
    }
  }
}
