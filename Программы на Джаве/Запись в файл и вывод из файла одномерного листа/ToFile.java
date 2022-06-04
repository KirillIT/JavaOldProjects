package com.company;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.PrintWriter;

import java.util.List;

public class ToFile { //запись листа в файл

    public void ToFile(List<Integer> list, String path) { //(лист, путь к файлу)

        File file = new File(path); //создаёт файл с путём, указанным пользователем

        PrintWriter pw = null;

        try {

            pw = new PrintWriter(file); //объект, записывающий информацию в файл

        }

        catch (FileNotFoundException ex) {

            ex.printStackTrace();

        }

        for (int i = 0; i < list.size(); i++) {

            pw.print(list.get(i) + " "); //записываем элементы листа

        }

        pw.close(); //закрываем файл

    }
}
