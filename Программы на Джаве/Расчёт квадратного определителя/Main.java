package com.company;

import java.util.Scanner;

import java.util.Locale;

public class Main {

    public static double calculate(double [][] arr) { //функция, считает определитель (квадратный) любой размерности

        double result = 0; //итоговый результат будет тут

        int startColIndex = 0; //мы будем идти по строке. То есть первая строка всегда не учитываться. А это индекс колонки, которую мы будем не учитывать

        if (arr.length  == 2) { //когда матрица стоновится второй размерности, то просто считаем её

            return (arr[0][0] * arr[1][1]) - (arr[0][1] * arr[1][0]); //расчёты

        }

        for (int i = 0; i < arr.length; i++) { //теперь для каждого элемента в строке образум свой новый определитель, который будет на размерность меньше исходного

            double [][] newArr = new double[arr.length - 1][arr.length - 1]; //тот самый новый определитель, который на размерность меньше исходного

            for (int k = 1, d = 0; k < arr.length; k++, d++) { //теперь запишем в него только нужные строки и столбцы (начинаем с одного, т.к. первую строку пропускаем)

                for (int j = 0, p = 0; j < arr.length; j++) {

                    if (j!= startColIndex) { //когда наш элемент будет равен индексу колонки, которую нужно не учитывать, то мы пропускаем этот элемент

                        newArr[d][p] = arr[k][j]; //если всё хорошо, то копируем значение

                        p++; //это отдельный индекс для массива, т.к. индексы нашего старого массива и нового будут расходться. Индекс 'd' - тоже нужен для этого

                    }
                }
            }

            result += Math.pow(-1, startColIndex) * arr[0][startColIndex] * calculate(newArr); //прибавляем к результату рекурсию
            //(то есть расчитывается отдельно отдельно определитель меньшего порядка) + текущий элемент (по которому мы не учитываем строку и слолбец) + (-1) в степени номера текущего элемента
            startColIndex++; //идём к следующему элементу

        }

        return result; //возвращаем результат

    }

    public static void main(String[] args) {

        Locale.setDefault(Locale.ROOT);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь файла (для считывания данных): "); //D:\1.txt или D:\3.txt

        String pathFromFile = scanner.nextLine(); //считываем путь файла (для считывания данных)

        double arr[][] = FromFile.FromFile(pathFromFile); //считываем определитель из файла

        System.out.print("Введите путь файла (для записи данных): "); //D:\2.txt

        String pathToFile = scanner.nextLine(); //считываем путь файла (для записи данных)

        ToFile.ToFile(calculate(arr), pathToFile); //записываем результат в файл

    }
}
