package com.company;

import java.io.File;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;

public class FromFile { //записывает данные в лист из файла

    public List<Integer> FromFile(String Path) { //возвращает ссылку лист

        File file = new File(Path); //ищет файл по путю Path

        Scanner scanner = null;

        List<Integer> list = new ArrayList<Integer>();

        try {

            scanner = new Scanner(file); //объект, считывающий данные из файла

        }

        catch (FileNotFoundException ex) {

            System.out.println("Error: 0");

            System.exit(0);

        }

        String[] s; //создаём временную строку

        String line; //ещё одна временная строка

        line = scanner.nextLine(); //берём всю строку

        s = line.split(" "); //знак разделения

        for (String number : s) {

            list.add(Integer.parseInt(number)); //записываем значения в временный массив

        }

        return list; //возвращаем готовый лист

    }
}
