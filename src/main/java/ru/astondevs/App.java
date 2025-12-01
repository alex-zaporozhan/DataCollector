package ru.astondevs;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //никаких циклов тут не делаю
        //создаю класс helper
        //для определенной директории где файлы для загрузки, проверяю есть ли файл для helper (искать файлы по маске)
        //из userHelper взять расширение и проверять есть ли файл в директории
        //если файл нашел - открываю файл на чтение InputStream который открылся передать в helper
        //если helper в методе readRecords вернул > 0, то нам необходимо эти записи записать
        //чтение файла должно выполняться в readRecords
        //закрытие InputStream должно быть в основном классе try resources

        //если нужно записать данные в финальный файл -- проверяем если существует файл с фин записями
        //если true тогда читаем через readRecords и его тоже
        //потом открываем на запись новый файл (старый удаляем) и передаем OutputStream в writeRecords try resources

        // между readRecords и writeRecords должен быть setSorting



    }
}
