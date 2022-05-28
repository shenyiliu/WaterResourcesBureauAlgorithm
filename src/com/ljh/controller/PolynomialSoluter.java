package com.ljh.controller;

/**
 * @author shenyi
 * 多项式拟合辅助类
 */
public class PolynomialSoluter {
    private double[][] matrix;
    private double[] result;
    private int order;

    public PolynomialSoluter() {

    }

    // 检查输入项长度并生成增广矩阵
    private boolean init(double[][] matrixA, double[] arrayB) {
        order = arrayB.length;
        if (matrixA.length != order) {
            return false;
        }
        matrix = new double[order][order + 1];
        for (int i = 0; i < order; i++) {
            if (matrixA[i].length != order) {
                return false;
            }
            for (int j = 0; j < order; j++) {
                matrix[i][j] = matrixA[i][j];
            }
            matrix[i][order] = arrayB[i];
        }
        result = new double[order];
        return true;
    }

    public double[] getResult(double[][] matrixA, double[] arrayB) {
        if (!init(matrixA, arrayB)) {
            return null;
        }

        // 高斯消元-正向
        for (int i = 0; i < order; i++) {

            // 如果当前行对角线项为0则与后面的同列项非0的行交换
            if (!swithIfZero(i)) {
                return null;
            }
            // 消元
            for (int j = i + 1; j < order; j++) {
                if (matrix[j][i] == 0) {
                    continue;
                }
                double factor = matrix[j][i] / matrix[i][i];
                for (int l = i; l < order + 1; l++) {
                    matrix[j][l] = matrix[j][l] - matrix[i][l] * factor;
                }
            }
        }

        // 高斯消元-反向-去掉了冗余计算
        for (int i = order - 1; i >= 0; i--) {
            result[i] = matrix[i][order] / matrix[i][i];
            for (int j = i - 1; j > -1; j--) {
                matrix[j][order] = matrix[j][order] - result[i] * matrix[j][i];
            }
        }
        return result;
    }

    private boolean swithIfZero(int i) {
        if (matrix[i][i] == 0) {
            int j = i + 1;

            // 找到对应位置非0的列
            while (j < order && matrix[j][i] == 0) {
                j++;
            }

            // 若对应位置全为0则无解
            if (j == order) {
                return false;
            } else {
                switchRows(i, j);
            }
        }
        return true;
    }

    private void switchRows(int i, int j) {
        double[] tmp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tmp;
    }
}

