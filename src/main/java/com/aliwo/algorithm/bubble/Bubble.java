package com.aliwo.algorithm.bubble;


/**
 * package_name:com.aliwo.algorithm.bubble
 *
 * @author:徐亚远 Date:2021/1/27 17:30
 * 项目名:Blog
 * Description:冒泡排序 工具类
 * 时间复杂度为:O(n^2)
 * 冒泡排序原理：
 * 一:比较相邻的元素,如果前一个元素比后一个元素大,则交换这两个元素的位置
 * 二:对每一对相邻元素做同样的操作,从开始第一队元素到结尾的最后一对元素,最终最后位置的元素就是最大值
 * Version: 1.0
 **/

public class Bubble {

    /**
     * @param a 对数组a中的元素进行排序
     */
    public static void sort(Comparable[] a) {
        // 外层比较次数
        for (int i = a.length - 1; i > 0; i--) {
            // 内层比较相邻元素的大小
            for (int j = 0; j < i; j++) {
                if (greater(a[j], a[j + 1])) {
                    exch(a, j, j + 1);
                }
            }
        }
        /*for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (greater(a[i], a[j])) {
                    exch(a, i, j);
                }
            }
        }*/
    }

    /**
     * @param v
     * @param w
     * @return boolean
     * 比较v元素是否大于w元素 升序排列
     */
    private static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    /**
     * @param a
     * @param i
     * @param j 数组元素i j 交换位置
     */
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
