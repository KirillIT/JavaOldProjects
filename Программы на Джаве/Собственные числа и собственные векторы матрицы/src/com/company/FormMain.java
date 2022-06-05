package com.company;

import javax.swing.*;

import java.awt.*;

public class FormMain extends JFrame {

    private JButton ButtonSort;

    private JButton ButtonToFile;

    private JButton ButtonFromFile;

    private JPanel FormMainF;

    private JTable tableMain;

    private JScrollPane ScrollMain;

    private JTextArea TextArea;

    double[][] GaussMethod(double[][] extendedMatrix) {

        try {

            boolean isZero;

            boolean tempBoolean;

            int lastLineNumber = extendedMatrix.length - 1;

            for (int i = 0; i  < extendedMatrix.length; i++) {

                isZero = true;

                tempBoolean = false;

                for (int j = 0; j < extendedMatrix[0].length -1; j++){

                    if (extendedMatrix[i][j] != 0) {

                        isZero = false;

                        break;

                    }
                }

                if (isZero && i < lastLineNumber) {

                    while (true) {

                        for (int j = 0; j < extendedMatrix[0].length - 1; j++) {

                            if (extendedMatrix[lastLineNumber][j] != 0) {

                                tempBoolean = true;

                                break;

                            }
                        }

                        if (tempBoolean) {

                            break;

                        }

                        lastLineNumber--;

                    }

                    if (i < lastLineNumber) {

                        Matrix.swapTwoLines(extendedMatrix, i, lastLineNumber);

                    }
                }
            }

            if (Matrix.calculateTheRankOfTheMatrix(extendedMatrix) == Matrix.calculateTheRankOfTheAugmentedMatrix(extendedMatrix)) {

                double coefficient;

                int initialIndexI = 0;

                int initialIndexJ= 0;

                int howManyLinesBelow;

                int min = extendedMatrix.length;

                if (min > extendedMatrix[0].length) {

                    min = extendedMatrix[0].length;

                }

                for (int i = 1 ; i  < min; i++) {

                    Matrix.reduceTheMatrixToASteppedForm(extendedMatrix);

                    if (Matrix.calculateTheRankOfTheMatrix(extendedMatrix) != Matrix.calculateTheRankOfTheAugmentedMatrix(extendedMatrix)) {

                        TextArea.setText("Нет решений");

                        break;

                    }

                    howManyLinesBelow = 1;

                    for (int j = i ; j  < extendedMatrix.length; j++) {

                        if (extendedMatrix[initialIndexI][initialIndexJ] == 0) {

                            continue;

                        }

                        if (howManyLinesBelow + initialIndexI== extendedMatrix.length) {

                            break;

                        }

                        coefficient = extendedMatrix[initialIndexI + howManyLinesBelow][initialIndexJ] / extendedMatrix[initialIndexI][initialIndexJ];

                        for (int k = initialIndexJ; k < extendedMatrix[0].length; k++) {

                            extendedMatrix[j][k] -= extendedMatrix[initialIndexI][k]*coefficient;

                            if (Math.abs(extendedMatrix[j][k]) < 0.000000000001) {

                                extendedMatrix[j][k] = 0;

                            }
                        }

                        howManyLinesBelow++;

                        if (howManyLinesBelow + initialIndexI== extendedMatrix.length) {

                            break;

                        }
                    }

                    initialIndexI++;

                    initialIndexJ++;

                }

                Matrix.reduceTheMatrixToASteppedForm(extendedMatrix);

                for (int i = 0; i  < extendedMatrix.length; i++ ) {

                    for (int j = 0; j < extendedMatrix[0].length; j++) {

                        if (extendedMatrix[i][j] < 0.0000000001 && extendedMatrix[i][j] > -0.0000000001){

                            extendedMatrix[i][j] = 0;

                        }
                    }
                }

                for (int i = 0; i  < extendedMatrix.length; i++ ) {

                    for (int j = 0; j < extendedMatrix[0].length; j++) {

                        if (extendedMatrix[i][j] != 0) {

                            break;

                        }

                        if (j == extendedMatrix[0].length - 1) {

                            extendedMatrix = Matrix.deleteString(extendedMatrix, i);

                            i--;

                        }
                    }
                }
            }

            else {

                TextArea.setText("Нет решений");

            }
        }

        catch (Exception a) {

            a.getMessage();

        }

        return extendedMatrix;

    }

    public FormMain() {

        this.setTitle("Собственные числа и собственные векторы матрицы");

        this.setContentPane(FormMainF);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        ru.vsu.cs.util.JTableUtils.initJTableForArray(tableMain, 40, true, true, true, true);

        ru.vsu.cs.util.JTableUtils.resizeJTable(tableMain, 3, 3);

        ButtonSort.addActionListener(e -> {

            String[][] rawArray;

            try {

                rawArray =  ru.vsu.cs.util.JTableUtils.readStringMatrixFromJTable(tableMain);

                double[][] extendedMatrix = new double[tableMain.getRowCount()][tableMain.getRowCount()+1];

                if (tableMain.getRowCount() != tableMain.getColumnCount() || tableMain.getRowCount() < 2 || tableMain.getRowCount() > 3) {

                    TextArea.setText("Недопустимый размер для матрицы!");

                }

                else {

                    double[][] temp1 = new double[tableMain.getRowCount()][tableMain.getRowCount() + 1];

                    double[][] temp2 = new double[tableMain.getRowCount()][tableMain.getRowCount() + 1];

                    double[][] temp3 = new double[tableMain.getRowCount()][tableMain.getRowCount() + 1];

                    for (int i = 0; i < tableMain.getRowCount(); i++) {

                        extendedMatrix[i][tableMain.getRowCount()] = 0;

                        for (int j = 0; j < tableMain.getRowCount(); j++) {

                            assert rawArray != null;

                            extendedMatrix[i][j] = Integer.parseInt(rawArray[i][j]);

                        }
                    }

                    for (int i = 0; i < extendedMatrix.length; i++) {

                        for (int j = 0; j < extendedMatrix[0].length; j++) {

                            temp1[i][j] = extendedMatrix[i][j];

                            temp2[i][j] = extendedMatrix[i][j];

                            temp3[i][j] = extendedMatrix[i][j];

                        }
                    }

                    if (tableMain.getRowCount() == 2) {

                        double a = 1, b, c, D, res1, res2;

                        b = extendedMatrix[0][0] * -1 + extendedMatrix[1][1] * -1;

                        c = extendedMatrix[0][0] * extendedMatrix[1][1] - extendedMatrix[0][1] * extendedMatrix[1][0];

                        D = b * b - 4 * a * c;

                        if (!(D < 0)) {

                            res1 = (Math.sqrt(D) - b) / (2 * a);

                            res2 = (-Math.sqrt(D) - b) / (2 * a);

                            temp1[0][0] -= res1;

                            temp1[1][1] -= res1;

                            temp1 = GaussMethod(temp1);

                            temp2[0][0] -= res2;

                            temp2[1][1] -= res2;

                            temp2 = GaussMethod(temp2);

                            String str = "";

                            double temp;

                            if (temp1.length == 1) {

                                temp = temp1[0][0];

                                for (int j = 0; j < temp1[0].length; j++) {

                                    temp1[0][j] /= temp;

                                }

                                str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + temp1[0][1] * -1 + "x2,\n" + "1.0x2\n" + "}\n\n";

                                str += "При λ = " + res1 + " получим собественные числа:\n{\n" + temp1[0][1] * -1 + ",\n" + "1.0\n" + "}\n\n";

                            }

                            else {

                                if (temp1[0][0] == 0){

                                    str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + "x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res1 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else {

                                    str += "При λ = " + res1 + " нет решений!";

                                }
                            }

                            if (temp2.length == 1) {

                                temp = temp2[0][0];

                                for (int j = 0; j < temp2[0].length; j++) {

                                    temp2[0][j] /= temp;

                                }

                                str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + temp2[0][1] * -1 + "x2,\n" + "1.0x2\n" + "}\n\n";

                                str += "При λ = " + res2 + " получим собественные числа:\n{\n" + temp2[0][1] * -1 + ",\n" + "1.0\n" + "}\n\n";

                            }

                            else {

                                if (temp2[0][0] == 0) {

                                    str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + "x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res2 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else {

                                    str += "При λ = " + res2 + " нет решений!";

                                }
                            }

                            TextArea.setText(str);

                        }

                        else {

                            TextArea.setText("Нет решений в действительныйх числах!");

                        }
                    }

                    else {

                        double a, b, c, res1, res2, res3,t, Q,R, sign,A,B;

                        a = -1*(extendedMatrix[2][2] + (extendedMatrix[0][0]* -1 + extendedMatrix[1][1]*-1)*-1);

                        b = -1*((extendedMatrix[0][0]* -1 + extendedMatrix[1][1]*-1)*extendedMatrix[2][2]+ extendedMatrix[0][0]* extendedMatrix[1][1]*-1  + extendedMatrix[0][2]*extendedMatrix[2][0] - -1*extendedMatrix[1][2]*extendedMatrix[2][1]- -1*extendedMatrix[0][1]*extendedMatrix[1][0]);

                        c = -1*(extendedMatrix[0][0]*extendedMatrix[1][1]*extendedMatrix[2][2] +  extendedMatrix[0][2]*extendedMatrix[1][0]*extendedMatrix[2][1] + extendedMatrix[0][1]*extendedMatrix[1][2]*extendedMatrix[2][0]-extendedMatrix[0][2]*extendedMatrix[1][1]*extendedMatrix[2][0]-extendedMatrix[0][0]*extendedMatrix[1][2]*extendedMatrix[2][1]-extendedMatrix[0][1]*extendedMatrix[1][0]*extendedMatrix[2][2]);

                        Q = (Math.pow(a, 2)-3*b)/9;

                        R = (2*Math.pow(a, 3)-9*a*b+27*c)/54;

                        if (Math.pow(R, 2)< Math.pow(Q, 3)) {

                            t = Math.acos(R/Math.sqrt(Math.pow(Q, 3)))/3;

                            res1 = -2*Math.sqrt(Q)*Math.cos(t) - a/3;

                            res2 = -2*Math.sqrt(Q)*Math.cos(t+2*Math.PI/3) - a/3;

                            res3 = -2*Math.sqrt(Q)*Math.cos(t-2*Math.PI/3) - a/3;

                            temp1[0][0] -=res1;

                            temp1[1][1] -=res1;

                            temp1[2][2] -=res1;

                            temp1 = GaussMethod(temp1);

                            temp2[0][0] -=res2;

                            temp2[1][1] -=res2;

                            temp2[2][2] -=res2;

                            temp2 = GaussMethod(temp2);

                            temp3[0][0] -=res3;

                            temp3[1][1] -=res3;

                            temp3[2][2] -=res3;

                            temp3 = GaussMethod(temp3);

                            String str="";

                            if (temp1.length == 3) {

                                if (temp1[0][0] == 0) {

                                    str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + "1.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res1 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else {

                                    str += "При λ = " + res1 + " нет решений!";

                                }
                            }

                            else {

                                str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + ((temp1[0][2] * -1) - temp1[1][2]*-1/temp1[1][1])/temp1[0][0] + "x3,\n" + temp1[1][2]*-1/temp1[1][1] +"x3,\n" + "1.0x3\n"+ "}\n\n";

                                str += "При λ = " + res1 + " получим собественные числа:\n{\n" + ((temp1[0][2] * -1) - temp1[1][2]*-1/temp1[1][1])/temp1[0][0] + ",\n" + temp1[1][2]*-1/temp1[1][1] +",\n" + "1.0\n"+ "}\n\n";

                            }

                            if (temp2.length == 3) {

                                if (temp2[0][0] == 0) {

                                    str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + "1.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res2 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else {

                                    str += "При λ = " + res2 + " нет решений!";

                                }
                            }

                            else {

                                str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + ((temp2[0][2] * -1) - temp2[1][2]*-1/temp2[1][1])/temp2[0][0] + "x3,\n" + temp2[1][2]*-1/temp2[1][1] +"x3,\n" + "1.0x3\n"+ "}\n\n";

                                str += "При λ = " + res2 + " получим собественные числа:\n{\n" + ((temp2[0][2] * -1) - temp2[1][2]*-1/temp2[1][1])/temp2[0][0] + ",\n" + temp2[1][2]*-1/temp2[1][1] +",\n" + "1.0\n"+ "}\n\n";

                            }

                            if (temp3.length == 3){

                                if (temp3[0][0] == 0) {

                                    str += "При λ = " + res3 + " получим собественный вектор:\n{\n" + "1.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res3 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else{

                                    str += "При λ = " + res3 + " нет решений!";

                                }
                            }

                            else {

                                str += "При λ = " + res3 + " получим собественный вектор:\n{\n" + ((temp3[0][2] * -1) - temp3[1][2]*-1/temp3[1][1])/temp3[0][0] + "x3,\n" + temp3[1][2]*-1/temp3[1][1] +"x3,\n" + "1.0x3\n"+ "}\n\n";

                                str += "При λ = " + res3 + " получим собественные числа:\n{\n" + ((temp3[0][2] * -1) - temp3[1][2]*-1/temp3[1][1])/temp3[0][0] + ",\n" + temp3[1][2]*-1/temp3[1][1] +",\n" + "1.0\n"+ "}\n\n";

                            }

                            TextArea.setText(str);

                        }

                        else {

                            if (R>= 0) {

                                sign = 1;

                            }

                            else {

                                sign = -1;

                            }

                            A = -sign*Math.pow(Math.abs(R)+Math.sqrt(Math.pow(R, 2) - Math.pow(Q, 3)),1/3.0);

                            if (A == 0) {

                                B = 0;

                            }

                            else {

                                B = Q / A;

                            }

                            res1 = (A + B) - a/3;

                            String str="";

                            temp1[0][0] -=res1;

                            temp1[1][1] -=res1;

                            temp1[2][2] -=res1;

                            temp1 = GaussMethod(temp1);

                            if (temp1.length == 3) {

                                if (temp1[0][0] == 0) {

                                    str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + "1.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                    str += "При λ = " + res1 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                }

                                else {

                                    str += "При λ = " + res1 + " нет решений!";

                                }
                            }

                            else {

                                str += "При λ = " + res1 + " получим собественный вектор:\n{\n" + ((temp1[0][2] * -1) - temp1[1][2]*-1/temp1[1][1])/temp1[0][0] + "x3,\n" + temp1[1][2]*-1/temp1[1][1] +"x3,\n" + "1.0x3\n"+ "}\n\n";

                                str += "При λ = " + res1 + " получим собественные числа:\n{\n" + ((temp1[0][2] * -1) - temp1[1][2]*-1/temp1[1][1])/temp1[0][0] + ",\n" + temp1[1][2]*-1/temp1[1][1] +",\n" + "1.0\n"+ "}\n\n";

                            }

                            if (A == B) {

                                res2 = res3 = -A - a/3;

                                temp2[0][0] -=res2;

                                temp2[1][1] -=res2;

                                temp2[2][2] -=res2;

                                temp2 = GaussMethod(temp2);

                                temp3[0][0] -=res3;

                                temp3[1][1] -=res3;

                                temp3[2][2] -=res3;

                                temp3 = GaussMethod(temp3);

                                if (temp2.length == 3) {

                                    if (temp2[0][0] == 0) {

                                        str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + "1.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                        str += "При λ = " + res2 + " получим собественные числа:\n{\n" + "1.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                    }

                                    else {

                                        str += "При λ = " + res2 + " нет решений!";

                                    }
                                }

                                else {

                                    str += "При λ = " + res2 + " получим собественный вектор:\n{\n" + ((temp2[0][2] * -1) - temp2[1][2]*-1/temp2[1][1])/temp2[0][0] + "x3,\n" + temp2[1][2]*-1/temp2[1][1] +"x3,\n" + "1.0x3\n"+ "}\n\n";

                                    str += "При λ = " + res2 + " получим собественные числа:\n{\n" + ((temp2[0][2] * -1) - temp2[1][2]*-1/temp2[1][1])/temp2[0][0] + ",\n" + temp2[1][2]*-1/temp2[1][1] +",\n" + "1.0\n"+ "}\n\n";

                                }

                                if (temp3.length == 3) {

                                    if (temp3[0][0] == 0) {

                                        str += "При λ = " + res3 + " получим собественный вектор:\n{\n" + "2.0x1,\n" + "0.0x1,\n" + "0.0x1\n" + "}\n\n";

                                        str += "При λ = " + res3 + " получим собественные числа:\n{\n" + "2.0,\n" + "0.0,\n" + "0.0\n" + "}\n\n";

                                    }

                                    else {

                                        str += "При λ = " + res3 + " нет решений!";

                                    }
                                }

                                else {

                                    str += "При λ = " + res3 + " получим собественный вектор:\n{\n" + (((temp3[0][2] * -1) - temp3[1][2]*-1/temp3[1][1])/temp3[0][0])*2 + "x3,\n" + 2*(temp3[1][2]*-1/temp3[1][1]) +"x3,\n" + "2.0x3\n"+ "}\n\n";

                                    str += "При λ = " + res3 + " получим собественные числа:\n{\n" + (((temp3[0][2] * -1) - temp3[1][2]*-1/temp3[1][1])/temp3[0][0])*2 + ",\n" + 2*(temp3[1][2]*-1/temp3[1][1]) +",\n" + "2.0\n"+ "}\n\n";

                                }
                            }

                            TextArea.setText(str);

                        }
                    }
                }
            }

            catch (Exception a) {

                a.getMessage();

            }
        });

        ButtonToFile.addActionListener(e -> {

            String[][] arrMain2 = new String[tableMain.getRowCount()][tableMain.getColumnCount()];

            try {

                arrMain2 =  ru.vsu.cs.util.JTableUtils.readStringMatrixFromJTable(tableMain);


            } catch (Exception a) {

                a.getMessage();

            }

            ToFile toFile = new ToFile();

            toFile.ToFile(arrMain2, tableMain.getRowCount(), tableMain.getColumnCount(), "C:\\Users\\newli\\IdeaProjects\\untitled\\src\\com\\company\\test.txt"); //а теперь записываем из матрицы в файл

        });

        ButtonFromFile.addActionListener(e -> {

            FromFile fromFile = new FromFile();

            String[][] fStr = fromFile.FromFile("C:\\Users\\newli\\IdeaProjects\\untitled\\src\\com\\company\\test.txt");

            ru.vsu.cs.util.JTableUtils.writeArrayToJTable(tableMain, fStr);

        });
    }

    //дальше барахолка

    {
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        FormMainF = new JPanel();
        FormMainF.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(10, 10, 10, 10), 10, 10));
        ButtonToFile = new JButton();
        ButtonToFile.setText("Загрузить в файл");
        FormMainF.add(ButtonToFile, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonFromFile = new JButton();
        ButtonFromFile.setText("Загрузить из файла");
        FormMainF.add(ButtonFromFile, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonSort = new JButton();
        ButtonSort.setHideActionText(false);
        ButtonSort.setText("Сортировать");
        FormMainF.add(ButtonSort, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ScrollMain = new JScrollPane();
        FormMainF.add(ScrollMain, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableMain = new JTable();
        ScrollMain.setViewportView(tableMain);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return FormMainF;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
