package com.company;

public class Matrix {

    public static double calculate(double [][] arr) {

        double result = 0;

        int startColIndex = 0;

        if (arr.length  == 2) {

            return (arr[0][0] * arr[1][1]) - (arr[0][1] * arr[1][0]);

        }

        if (arr.length  == 1) {

            return arr[arr.length-1][arr.length - 1];

        }

        for (int i = 0; i < arr.length; i++) {

            double [][] newArr = new double[arr.length - 1][arr.length - 1];

            for (int k = 1, d = 0; k < arr.length; k++, d++) {

                for (int j = 0, p = 0; j < arr.length; j++) {

                    if (j!= startColIndex) {

                        newArr[d][p] = arr[k][j];

                        p++;

                    }
                }
            }

            result += Math.pow(-1, startColIndex) * arr[0][startColIndex] * calculate(newArr); //прибавляем к результату рекурсию

            startColIndex++;

        }

        return result;

    }

    public static double[][] temp(double [][] arr, int x, int y) {

        double tempArr [][] = new double[arr.length - 1][arr.length - 1];

        int realI = 0, realJ;

        for (int i = 0; i < arr.length; i++) {

            if (i == x) {

                continue;

            }

            realJ = 0;

            for (int j = 0; j < arr.length; j++) {

                if (j == y) {

                    continue;

                }

                tempArr[realI][realJ] = arr[i][j];

                realJ++;

            }

            realI++;

        }

        return tempArr;

    }

    public static double [][] validation(double [][]fArr, double [][]sArr){

        double finalArr[][] = new double[fArr.length][sArr[0].length];

        for (int i = 0; i < fArr.length; i++) {

            for (int j = 0; j < sArr[0].length; j++) {

                finalArr[i][j] = 0;

            }
        }

        int x = 0, y;

        for (int i = 0; i < fArr.length; i++) {

            y = 0;

            for (int j = 0; j < sArr[0].length; j++) {

                for (int k = 0; k < fArr.length; k++) {

                    finalArr[i][j] += fArr[x][k] * sArr[k][y];

                }

                y++;

            }

            x++;

        }

        return finalArr;

    }

    public static double [][] calculateInverseMatrix(double [][] arr) {

        double newArr [][] = new double[arr.length][arr.length];

        double det = calculate(arr);

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length; j++) {


                newArr[i][j] = calculate(temp(arr, i, j)) * Math.pow(-1, i + j);

            }
        }

        newArr = transposition(newArr);

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length; j++) {

                newArr[i][j] /= det;

                if (newArr[i][j] == -0) {

                    newArr[i][j] = 0;

                }

            }
        }

        return newArr;

    }

    public static double [][] transposition(double [][] arr) {

        double newArr [][] = new double[arr.length][arr.length];

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length; j++) {

                newArr[i][j] = arr[j][i];

            }
        }

        return newArr;

    }
}
