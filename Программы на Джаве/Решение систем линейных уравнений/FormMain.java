package com.company;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class FormMain extends JFrame {

    private JButton ButtonSort;

    private JButton ButtonToFile;

    private JButton ButtonFromFile;

    private JPanel FormMainF;

    private JTable tableMain;

    private JScrollPane ScrollMain;

    private JTextArea TextArea;

    public FormMain() {

        this.setTitle("Решение систем линейных уравнений");

        this.setContentPane(FormMainF);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        ru.vsu.cs.util.JTableUtils.initJTableForArray(tableMain, 40, true, true, true, true);

        ru.vsu.cs.util.JTableUtils.resizeJTable(tableMain, 4, 5);

        ButtonSort.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {


                String[][] arrMain;

                double[][] arrMain2 = new double[tableMain.getRowCount()][tableMain.getColumnCount()/3];

                double[][] arrMain3 = new double[tableMain.getRowCount()][1];

                double[][] result;

                try {

                    arrMain =  ru.vsu.cs.util.JTableUtils.readStringMatrixFromJTable(tableMain);

                    int temp1 = 0, temp2;

                    int k;

                    for (int i = 0; i  <tableMain.getRowCount(); i++ ) {

                        temp2 = 0;

                        for (int j = 0; j < tableMain.getColumnCount() - 2; j++){

                            if (j == 0) {

                                arrMain2[temp1][temp2++] = Integer.parseInt(arrMain[i][j]);

                            }

                            else if (j%3 == 1 || j % 3 == 2) {

                                continue;

                            }

                            else {

                                if (arrMain[i][j-1].toCharArray()[0]=='-') {

                                    k = -1;

                                }
                                else {

                                    k = 1;

                                }

                                arrMain2[temp1][temp2++] = Integer.parseInt(arrMain[i][j])* k;

                            }
                        }

                        temp1++;

                    }

                    for (int i = 0; i < tableMain.getRowCount(); i++){

                        arrMain3[i][0] =Integer.parseInt(arrMain[i][tableMain.getColumnCount() - 1]);
                    }

                    result = Matrix.validation(Matrix.calculateInverseMatrix(arrMain2), arrMain3);

                    String resultat = "";

                    for (int i = 0; i < result.length; i++) {

                        for (int j = 0; j < result[0].length; j++){

                            resultat += ("x" + (i + 1) + " = " + result[i][j] + '\n');

                        }
                    }

                    TextArea.setText(resultat);

                } catch (Exception a) {

                    a.getMessage();

                }
            }
        });

        ButtonToFile.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String[][] arrMain;

                String[][] arrMain2 = new String[tableMain.getRowCount()][tableMain.getColumnCount() - tableMain.getColumnCount()/3];

                try {

                    arrMain =  ru.vsu.cs.util.JTableUtils.readStringMatrixFromJTable(tableMain);

                    int temp1 = 0, temp2;

                    for (int i = 0; i  <tableMain.getRowCount(); i++ ) {

                        temp2 = 0;

                        for (int j = 0; j < tableMain.getColumnCount(); j++){

                            if (j%3 == 1) {

                                continue;

                            }

                            else {

                                arrMain2[temp1][temp2++] = arrMain[i][j];

                            }
                        }

                        temp1++;

                    }

                } catch (Exception a) {

                    a.getMessage();

                }

                ToFile toFile = new ToFile();

                toFile.ToFile(arrMain2, tableMain.getRowCount(), tableMain.getColumnCount() - tableMain.getColumnCount()/3, "C:\\Users\\newli\\IdeaProjects\\untitled\\src\\com\\company\\test.txt"); //а теперь записываем из матрицы в файл

            }
        });

        ButtonFromFile.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                FromFile fromFile = new FromFile();

                String[][] fStr = fromFile.FromFile("C:\\Users\\newli\\IdeaProjects\\untitled\\src\\com\\company\\test.txt");

                String[][] sStr = new String[fStr.length][fStr[0].length / 2 + fStr[0].length];

                int temp1 = 0, temp2;

                for (int i = 0; i  <fStr.length; i++ ) {

                    temp2 = 0;

                    for (int j = 0; j < fStr[0].length / 2 + fStr[0].length; j++) {

                        if (j % 3 == 1) {

                            sStr[i][j] = "x" + (j/3 + 1);

                        }

                        else {

                            sStr[i][j] = fStr[temp1][temp2++];

                        }
                    }

                    temp1++;

                }

                ru.vsu.cs.util.JTableUtils.writeArrayToJTable(tableMain, sStr);

            }
        });
    }

    //дальше барахолка

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
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

}
