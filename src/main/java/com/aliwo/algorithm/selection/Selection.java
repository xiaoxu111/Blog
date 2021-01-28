package com.aliwo.algorithm.selection;

/**
 * package_name:com.aliwo.algorithm.selection
 *
 * @author:徐亚远 Date:2021/1/27 18:59
 * 项目名:Blog
 * Description:选择排序工具类:
 * 选择排序原理:
 * 一:每一次遍历的过程中,都假定第一个索引处的元素是最小值,和其他索引处的值依次进行比较,如果当前索引处的值大于其他某个索引处的值,则假定其他某个索引处的值为最小值,最后可以找到最小值所在的索引。
 * 二:交换第一个索引处的值和最小值所在索引处的位置
 *  时间复杂度 O(n^2)
 * Version: 1.0
 **/

public class Selection {
    /**
     * @param a 对数组a中的元素进行排序
     */
    public static void sort(Comparable a[]) {
        // 需要进行的次数,a.length-2 因为是只有最低剩余两个元素的时候需要进行交换
        for (int i = 0; i <= a.length - 2; i++) {
            // 假定最小值所在处的索引为第一个元素i的位置
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                // 需要比较最小索引minIndex处的值和j索引处的值
                if (greater(a[minIndex], a[j])) {
                    minIndex = j;
                }
            }
            // 交换 最小元素minIndex索引处的值和索引i的值
            exch(a, minIndex, i);
        }
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
