package com.aliwo.algorithm.insertion;

/**
 * package_name:com.aliwo.algorithm.insertion
 *
 * @author:徐亚远 Date:2021/1/27 20:48
 * 项目名:Blog
 * Description:插入排序工具类
 * 原理:
 * 一：把所有元素分成两组,已经排序的和未排序的
 * 二：找到未排序的组中第一个元素,向已经排序的组中进行插入
 * 三：倒叙遍历已经排序的元素,依次和待插入的元素进行比较,直到找到一个元素小于等于待插入元素,那么把待插入元素放到这个位置,其他的元素向后移动一位。
 * Version: 1.0
 **/

public class Insertion {

    /**
     * @param a 对数组a中的元素进行排序
     */
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                // 比较索引j处的值和索引j-1处的值,如果索引j-1处的值比索引j处的值大,则交换数据
                if (greater(a[j - 1], a[j])) {
                    exch(a, j, j - 1);
                } else {
                    // 如果不大,那么找到合适的位置了,退出循环
                    break;
                }
            }
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
