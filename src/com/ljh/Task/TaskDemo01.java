package com.ljh.Task;

import com.ljh.controller.LeastSquare;
import com.ljh.controller.demo01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskDemo01 {
    public static void main(String[] args) {


        /**
         * 方法一
         * 根据给定的中断面两个坐标值，算出中段面的直线方程Ax+By+C=0   算出a,b,c的值
         * 算出上下断面 TopC,BottenC的值，因为三个断面平行，所以只有直线方程Ax+By+C=0 中的C改变。
         * @param x1  点一：（x1,y1）
         * @param y1
         * @param x2  点二：（x2,y2）
         * @param y2
         * @param d   中断面到上断面的距离d
         */
        double[][] str = demo01.XianDuan(1, 2, 300, 400, 100);
        for (double[] doubles : str) {
            for (double aDouble : doubles) {
                System.out.println(aDouble);
            }
            System.out.println();
        }
        System.out.println("===================方法一========================");


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
        Map<String, Double> hashMap = demo01.CursorCenter(400.0, 500.0, 50);
        System.out.println(
                "verticalX=" + hashMap.get("verticalX") + "\n" +
                "verticalY=" + hashMap.get("verticalY") + "\n" +
                "code=" + hashMap.get("code") + "\n"
        );
        System.out.println("================CursorTop=======================");

        /**
         * 方法二
         * 判断一个坐标点是否在上下断面之间。
         * @param x1    坐标点（x1,y1）
         * @param y1
         * @return 1为上下断面之内  0为上下断面之外
         */
        int num = demo01.YouBiao(5, 10);
        System.out.println(num);
        System.out.println("====================方法二=======================");


        /**
         * 计算一个游标的平均值  map集合需要创建这个对象 LinkedHashMap<>();需要保证存入游标的顺序
         * @param mapList 游标点坐标集合x,y
         * @param t 时间间隔
         * @return 返回游标点的平均速度
         */
        Map<Double, Double> map = new LinkedHashMap<>();
        map.put(1.0, 5.0);
        map.put(7.0, 6.0);
        map.put(10.0, 8.0);
        double v = demo01.AvgYB(map, 2);
        System.out.println("该游标点的平均速度为："+v);
        System.out.println("====================方法三=======================");

        /**
         * 方法四
         * 计算中断面固定点的坐标
         * @param x     中断面上方的初始量，基站点x,y
         * @param y
         * @param distanceLists   中断面上固定点到基站点的距离d的集合
         * @return 返回中断面固定点的坐标集合
         */
        ArrayList<Double> distanceLists = new ArrayList<>();
        for (double i = 5; i <= 30; i += 5) {
            distanceLists.add(i);
        }

        LinkedHashMap<Double, Double> list1 = demo01.fixedCursorCoordinate(300, 400, distanceLists);
        for (Double key : list1.keySet()) {
            System.out.println("key:" + key + "||value:" + list1.get(key));
        }
        System.out.println("====================方法四=======================");


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
        double[] x={50,60,70,80,90,100,110,120,130,140};
        double[] y={60,70,80,90,100,110,120,130,140,150};
        double[] V={10,12,13,14,15,16,15,14,13,12};
        double[] fixedX={55,65,75,85,95,105,115,125,135,145};
        double[] fixedY={65,75,85,95,105,115,125,135,145,155};
        double[] fixedV=null;
        int order=3;
        fixedV=demo01.curveFitting(x,y,V,fixedX,fixedY,order);
        for (int i = 0; i < fixedV.length; i++) {
            System.out.println(fixedV[i]);
        }
        System.out.println("====================方法五=======================");

    }
}
