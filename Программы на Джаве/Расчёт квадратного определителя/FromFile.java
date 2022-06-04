package com.company;

import java.io.File;

import java.io.FileNotFoundException;

import java.util.Scanner;

public class FromFile { //записывает данные в матрицу из файла

    public static double[][] FromFile(String Path) { //возвращает ссылку на двумерный массив

        File file = new File(Path); //ищет файл по путю Path

        Scanner scanner = null;

        try {

            scanner = new Scanner(file); //объект, считывающий данные из файла

        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

        }

        double[] tempArr = new double[100]; //создаём временный массив на N элементов

        String[] s; //создаём временную строку на N элементов

        String line; //ещё одна временная строка

        int counter; //счёткчик

        int countCols = 0; //кол-во колонок

        int counterRows = 0; //кол-во строк

        double[][] arrMain = new double[100][100]; //создаём временную матрицу на M*N элементов

        for (int i = 0; ; i++) { //записываем данные из файла в матрицу

            counterRows++; //счёткик_строк ++

            line = scanner.nextLine(); //берём всю строку

            s = line.split(" "); //знак разделения

            counter = 0; //обнуляем счётчик

            for (String number : s) {

                tempArr[counter++] = Double.parseDouble(number); //записываем значения в временный массив

                if (i == 0 || countCols < counter) {

                    countCols++; //увеличиваем счётчик колонок

                }
            }

            for (int j = 0; j < tempArr.length; j++) {

                arrMain[i][j] = tempArr[j]; //записываем готовую строку в наш псевдоконечный массив

            }

            for (int j = 0; j < countCols; j++) {

                tempArr[j] = 0; //временный массив зануляем (нужно, если в строке из N чисел используется только M, M < N)

            }

            if (!scanner.hasNextLine()) { //когда строки заканчиваются, то выходим из цикла

                break;

            }
        }

        double[][] trueArrMain = new double[counterRows][countCols]; //создаём уже конечный массив с реально использующимися размерами

        for (int i = 0; i < counterRows; i++) {

            for (int j = 0; j < countCols; j++) {

                trueArrMain[i][j] = arrMain[i][j]; //записываем в него данные

            }
        }

        return trueArrMain; //возвращаем конечный массив

    }
}
