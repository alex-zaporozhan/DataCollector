package ru.astondevs;

import ru.astondevs.data.StudentHelper;
import ru.astondevs.data.UserHelper;
import ru.astondevs.model.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

  public static void main(String[] args) throws InterruptedException {
    if (args.length == 0) {
      System.err.println("Please provide folder for data inputs");
      return;
    }

    String sourceFolderPath = args[0];
    String targetFolderPath = args[1];
    String trashFolderPath = args[2];
    File sourceFolder = new File(sourceFolderPath);
    File targetFolder = new File(targetFolderPath);
    File trashFolder = new File(trashFolderPath);

    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      System.err.println("Invalid folder path: " + sourceFolderPath);
      System.out.println("Source folder path must be the first parameter");
      return;
    }
    if (!targetFolder.exists() || !targetFolder.isDirectory()) {
      System.err.println("Invalid folder path: " + targetFolderPath);
      System.out.println("Source folder path must be the second parameter");
      return;
    }

    List<Helper> helpers = new ArrayList<>();
    helpers.add(new UserHelper());
    helpers.add(new StudentHelper());
    // TODO Add your helpers here

    Runnable mainThread = () -> {
      while (true) {
        //System.out.println("Checking files in "+sourceFolderPath);
        File[] files = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        for (File file : files) {
          handleNewFile(file, helpers, targetFolder, trashFolder);
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.exit(0);
        }
      }
    };
    Thread thread = new Thread(mainThread);
    thread.start();
    thread.join();
  }

  private static void handleNewFile(File file, List<Helper> helpers, File targetFolder, File trashFolder) {
    Optional<Helper> possibleHelper = helpers.stream().filter(h -> file.getName().endsWith(h.getExtention())).findFirst();
    if (!possibleHelper.isEmpty()) {
      Helper helper = possibleHelper.get();
      readFileWithHelper(file, helper);
      File[] files = targetFolder.listFiles(f -> f.getName().endsWith(helper.getExtention()));
      if (files.length > 0 ) {
        readFileWithHelper(files[0], helper);
        files[0].delete();
      }
      try (OutputStream outputStream = new FileOutputStream(targetFolder.getAbsolutePath() + "/data." + helper.getExtention())) {
        helper.writeRecords(outputStream);
      } catch (IOException e) {
        System.err.println("Error writing file " + file.getName() + ": " + e.getMessage());
        System.exit(-1);
      }
      if (trashFolder.exists()) {
        file.renameTo(new File(trashFolder.getAbsolutePath() + "/" + file.getName()));
      } else {
        file.delete();
      }
    }
  }

  private static void readFileWithHelper(File file, Helper helper) {
    System.out.println("Processing file: " + file.getName());
    try (InputStream inputStream = new FileInputStream(file)) {
      // Pass inputStream to helper for processing
      if (helper.readRecords(inputStream) == -1) {
        System.out.println("File is broken");
        System.exit(-1);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
