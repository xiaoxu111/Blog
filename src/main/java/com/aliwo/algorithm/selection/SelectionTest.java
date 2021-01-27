package com.aliwo.algorithm.selection;


import java.util.Arrays;

/**
 * package_name:com.aliwo.algorithm.selection
 *
 * @author:徐亚远 Date:2021/1/27 19:44
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/

public class SelectionTest {
    public static void main(String[] args) {
        Integer[] arr = {2, 4, 6, 1, 5, 3};
        Selection.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
