package com.ljh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class demo01 {
    private static double a, b, c;

    private static double TopC, BottenC;


    //根据给定的中断面两个坐标值，以及d点坐标，求得上下断面坐标点
    //参数  点一：（x1,y1）     点二：（x2,y2）    上断面 到 中断面 的距离d
    /*
        已知直线上的两点P1(X1,Y1) P2(X2,Y2)， P1 P2两点不重合。则直线的一般式方程AX+BY+C=0中，A B C分别等于：
        A = Y2 - Y1
        B = X1 - X2
        C = X2*Y1 - X1*Y2
     */

    /**
     * 方法一
     * 根据给定的中断面两个坐标值，算出中段面的直线方程Ax+By+C=0   算出a,b,c的值
     * 算出上下断面 TopC,BottenC的值，因为三个断面平行，所以只有直线方程Ax+By+C=0 中的C改变。
     *
     * @param x1 点一：（x1,y1）
     * @param y1
     * @param x2 点二：（x2,y2）
     * @param y2
     * @param d  中断面到上断面的距离d
     * @return 返回值 二维数组：  上断面两个点x,y坐标[0][0~3](x1,y1,x2,y2)     下断面两个点坐标[1][0~3](x1,y1,x2,y2)
     * <p>
     * 注意：上断面（0，y）上断面起始点必须在y轴上      下断面（x,0）起始点必须在x轴上
     */
    public static double[][] XianDuan(double x1, double y1, double x2, double y2, double d) {
        //计算中断面直线方程
        a = y2 - y1;
        b = x1 - x2;
        c = x2 * y1 - x1 * y2;
        //返回a,b,c的值
        System.out.println("中断面方程：" + a + "=>" + b + "=>" + c);

        //计算上断面方程  a,b不变  只改变c值
        TopC = c + d * Math.sqrt((a * a + b * b));
        System.out.println("上断面方程：" + a + "=>" + b + "=>" + TopC);

        //计算下断面方程  a,b不变  只改变c值
        BottenC = c - d * Math.sqrt((a * a + b * b));
        System.out.println("下断面方程：" + a + "=>" + b + "=>" + BottenC);

        //计算上断面坐标
        double[][] number = new double[2][4];
        double Sx1, Sy1, Sx2, Sy2;
        Sx1 = 0;
        Sy1 = -TopC / b;

        Sx2 = (b * b / a * x2 - b * y2 - TopC) / (a + b * b / a);
        Sy2 = b / a * (Sx2 - x2) + y2;
        number[0][0] = Sx1;
        number[0][1] = Sy1;
        number[0][2] = Sx2;
        number[0][3] = Sy2;

        //计算下断面坐标
        double Xx1, Xy1, Xx2, Xy2;
        Xx1 = -BottenC / a;
        Xy1 = 0;

        Xx2 = (b * b / a * x2 - b * y2 - BottenC) / (a + b * b / a);
        Xy2 = b / a * (Xx2 - x2) + y2;

        number[1][0] = Xx1;
        number[1][1] = Xy1;
        number[1][2] = Xx2;
        number[1][3] = Xy2;

        //返回上断面，下断面的坐标点
        return number;
    }

//**************************************************************
    //下面一共四个方法，只需调用前三个就可以了
        /**
         * 判断上断面误差
         * @param x 游标x值
         * @param y 游标y值
         * @param d 误差范围d
         * @return Map<String, Double>
         * cursorMap.put("verticalX",verticalX);  //返回游标x坐标
         * cursorMap.put("verticalY",verticalY);  //返回游标y坐标
         * cursorMap.put("code",1.0);             //code:为1时 在误差内，为0时在误差外
         */
        public static Map<String, Double> CursorTop(double x, double y, double d){
            return  CursorError(x,y,d,TopC);
        }

        /**
         * 判断中断面误差
         * @param x 游标x值
         * @param y 游标y值
         * @param d 误差范围d
         * @return Map<String, Double>
         * cursorMap.put("verticalX",verticalX);  //返回游标x坐标
         * cursorMap.put("verticalY",verticalY);  //返回游标y坐标
         * cursorMap.put("code",1.0);             //code:为1时 在误差内，为0时在误差外
         */
        public static Map<String, Double> CursorCenter(double x, double y, double d){
            return  CursorError(x,y,d,c);
        }

        /**
         * 判断下断面误差
         * @param x 游标x值
         * @param y 游标y值
         * @param d 误差范围d
         * @return Map<String, Double>
         * cursorMap.put("verticalX",verticalX);  //返回游标x坐标
         * cursorMap.put("verticalY",verticalY);  //返回游标y坐标
         * cursorMap.put("code",1.0);             //code:为1时 在误差内，为0时在误差外
         */
        public static Map<String, Double> CursorDown(double x, double y, double d){
            return  CursorError(x,y,d,BottenC);
        }


        /**
         *
         * 判断游标在断面一定距离内，近似为游标在断面上
         * @param x 游标x值
         * @param y 游标y值
         * @param d 误差范围d
         * @param c 所要判断的断面，方程 c的值（TopC:上断面的c值, BottenC：下断面的c值，c：中断面的c值）
         * @return Map<String, Double>
         * cursorMap.put("verticalX",verticalX);  //返回游标x坐标
         * cursorMap.put("verticalY",verticalY);  //返回游标y坐标
         * cursorMap.put("code",1.0);             //code:为1时 在误差内，为0时在误差外
         */
        public static Map<String, Double> CursorError(double x, double y, double d, double c) {
            Map<String, Double> cursorMap = new HashMap<>(16);
            //垂直面的斜率
            double verticalK = b / a;
            //计算垂直线段的方程
            double verticalB = y - verticalK * x;
            double verticalX = -(c + verticalB*b)/(a+b*verticalK);
            double verticalY = verticalK * verticalX + verticalB;
            //计算点到直线的距离
            double verticalD = Math.abs(Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b));
            System.out.println("d="+verticalD);

            //若在误差范围内返回游标x，y坐标,code：1
            if (verticalD <= (d / 2)) {
                cursorMap.put("verticalX", verticalX);
                cursorMap.put("verticalY", verticalY);
                cursorMap.put("code", 1.0);
            } else {//若不在范围内，返回游标x，y坐标为-1,code：0
                cursorMap.put("verticalX", 0.0);
                cursorMap.put("verticalY", 0.0);
                cursorMap.put("code", 0.0);
            }
            return cursorMap;
        }
//********************************************************

    /**
     * 方法二
     * 判断一个坐标点是否在上下断面之间。
     *
     * @param x1 坐标点（x1,y1）
     * @param y1
     * @return 1为上下断面之内  0为上下断面之外
     */
    public static int YouBiao(double x1, double y1) {

        //上断面：ax+by+TopC=0   下断面：ax+by+BottenC=0。
        double tempY1, tempY2;
        int count = 0;
        //上断面y值
        tempY1 = -(TopC + a * x1) / b;
        //下断面y值
        tempY2 = -(BottenC + a * x1) / b;

        if (tempY1 > y1 && tempY2 < y1) {
            count = 1;
        }
        return count;

    }


    //计算一个游标的平均值  map集合需要创建这个对象 LinkedHashMap<>();
    //参数:获取到上断面~下断面之间的游标坐标集合，获取游标坐标的间隔时间t
    //返回值：返回中断面游标的平均值

    /**
     * 计算一个游标的平均值  map集合需要创建这个对象 LinkedHashMap<>();需要保证存入游标的顺序
     * @param mapList 游标点坐标集合x,y
     * @param t 时间间隔
     * @return 返回游标点的平均速度
     */
    public static double AvgYB(Map<Double, Double> mapList,double t){

        ArrayList<Double> xList=new ArrayList<>();
        ArrayList<Double> yList=new ArrayList<>();

        for (Map.Entry<Double,Double> entry: mapList.entrySet()){
            xList.add(entry.getKey());
            yList.add(entry.getValue());
            System.out.println("x:"+entry.getKey()+"|y:"+entry.getValue());
        }

        int count=0;
        double sum=0;
        for (int i = 0; i < xList.size()-1; i++) {
            count++;
            double num=Math.pow(xList.get(i)-xList.get(i+1),2)+Math.pow(yList.get(i)-yList.get(i+1),2);
            double avgItem=Math.abs(Math.sqrt(num))/t;
            sum+=avgItem;
        }

        return sum/count;
    }


    /**
     * 方法四
     * 计算中断面固定点的坐标
     *
     * @param x             中断面上方的初始量，基站点x,y
     * @param y
     * @param distanceLists 中断面上固定点到基站点的距离d的集合
     * @return 返回中断面固定点的坐标集合
     */
    public static LinkedHashMap<Double, Double> fixedCursorCoordinate(double x, double y, ArrayList<Double> distanceLists) {

        double k = -a / b;
        //存储中断面固定点坐标。
        LinkedHashMap<Double, Double> listMap = new LinkedHashMap<>();
        for (Double d : distanceLists) {
            double x1 = x - (d / Math.sqrt(1 + k * k));
            double y1 = y - (d / Math.sqrt(1 + 1 / k * k));
            listMap.put(x1, y1);
        }
        return listMap;
    }

    /**
     * 创建多项式曲线回归模型
     * @param x 游标点在中断面的x坐标
     * @param y 游标点在中断面的y坐标
     * @param v 游标点在中断面的速度v
     * @param fixedX 固定点x的坐标
     * @param fixedY 固定点y的坐标
     * @param order 为进行拟合的阶数。用低阶来拟合高阶曲线时可能会不准确，但阶数过高会导致计算缓慢，建议参数为：3
     * @return 返回中断面固定点的速度数组。
     */
    public static double[]  curveFitting(double[] x,double[] y,double[] v,double[] fixedX,double[] fixedY,int order){
        double[] curveX=new double[x.length];
        double[] curveY=v;
        double[] curveFixedX=new double[fixedX.length];
        double[] curveFixedV=new double[fixedX.length];
        //计算固定点在曲线拟合的x坐标点
        for (int i = 0; i < fixedX.length; i++) {
            curveFixedX[i]=Math.sqrt(Math.pow(fixedX[i],2)+Math.pow(fixedY[i],2));
        }

        //创建拟合多项式的对象
        LeastSquare leastSquare = new LeastSquare();
        //计算回归曲线的x坐标
        for (int i = 0; i < x.length; i++) {
            curveX[i]=Math.sqrt(Math.pow(x[i],2)+Math.pow(y[i],2));
        }
        boolean b = leastSquare.generateFormula(curveX, curveY, order);
        System.out.println(b);

        for (int i = 0; i < fixedX.length; i++) {
            curveFixedV[i]=leastSquare.calculate(curveFixedX[i]);
        }
        return curveFixedV;
    }




}
