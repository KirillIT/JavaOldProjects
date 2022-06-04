package com.company;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.PrintWriter;

public class ToFile { //запись числа в файл

    public static void ToFile(double number, String path) { //(число, путь к файлу)

        File file = new File(path); //создаёт файл с путём, указанным пользователем

        PrintWriter pw = null;

        try {

            pw = new PrintWriter(file); //объект, записывающий информацию в файл

        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

        }

        pw.print(number); //записываем элементы массива

        pw.close(); //закрываем файл

    }
}
