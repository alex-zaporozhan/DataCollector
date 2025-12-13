package ru.astondevs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Helper {
    String CSV_DELIMITER = ";";
    String getExtention(); //должен возвращять расширение файлов
    int readRecords(InputStream inputStream); //-1 произошла ошибка; 0 нет записи; или число записей которые прочитали
    boolean writeRecords(OutputStream outputStream); //возвращает false если произошла ошибка, нужно заканчивать приложение
    void setSorting(String field);//настраивает сортировку
}
