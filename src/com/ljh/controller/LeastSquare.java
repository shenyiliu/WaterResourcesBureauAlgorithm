package com.ljh.controller;

/**
 * @author shenyi
 * 多项式曲线拟合
 */
public class LeastSquare {
    private double[][] matrixA;
    private double[] arrayB;
    private double[] factors;
    private int order;

    public LeastSquare() {

    }
    /**
     * 实例化后，计算前，先要输入参数并生成公式 arrayX为采样点的x轴坐标，按照采样顺序排列
     * arrayY为采样点的y轴坐标，按照采样顺序与x一一对应排列 order
     * 为进行拟合的阶数。用低阶来拟合高阶曲线时可能会不准确，但阶数过高会导致计算缓慢
     * @param arrayX  x坐标数组
     * @param arrayY  y坐标数组
     * @param order   为进行拟合的阶数
     * @return
     */
    public boolean generateFormula(double[] arrayX, double[] arrayY, int order) {
        if (arrayX.length != arrayY.length) {
            return false;
        }
        this.order = order;
        int len = arrayX.length;

        // 拟合运算中的x矩阵和y矩阵
        matrixA = new double[order + 1][order + 1];
        arrayB = new double[order + 1];

        // 生成y矩阵以及x矩阵中幂<=order的部分
        for (int i = 0; i < order + 1; i++) {
            double sumX = 0;
            for (int j = 0; j < len; j++) {
                double tmp = Math.pow(arrayX[j], i);
                sumX += tmp;
                arrayB[i] += tmp * arrayY[j];

            }
            for (int j = 0; j <= i; j++) {
                matrixA[j][i - j] = sumX;
            }
        }

        // 生成x矩阵中幂>order的部分
        for (int i = order + 1; i <= order * 2; i++) {
            double sumX = 0;
            for (int j = 0; j < len; j++) {
                sumX += Math.pow(arrayX[j], i);
            }

            for (int j = i - order; j < order + 1; j++) {
                matrixA[i - j][j] = sumX;
            }
        }

        // 实例化PolynomiaSoluter并解方程组，得到各阶的系数序列factors
        PolynomialSoluter soluter = new PolynomialSoluter();
        factors = soluter.getResult(matrixA, arrayB);
        if (factors == null) {
            return false;
        } else {
            return true;
        }
    }

    // 根据输入坐标，以及系数序列factors计算指定坐标的结果
    public double calculate(double x) {
        double result = factors[0];
        for (int i = 1; i <= order; i++) {
            result += factors[i] * Math.pow(x, i);
        }
        return result;
    }

}
