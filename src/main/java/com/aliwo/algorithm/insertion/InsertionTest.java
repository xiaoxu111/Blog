package com.aliwo.algorithm.insertion;

import org.apache.ibatis.annotations.Insert;

import java.util.Arrays;

/**
 * package_name:com.aliwo.algorithm.insertion
 *
 * @author:徐亚远 Date:2021/1/27 21:13
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/

public class InsertionTest {
    public static void main(String[] args) {
        Integer [] arr = {2,5,3,1,7,10};
        Insertion.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
