package com.aliwo.algorithm.bubble;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * package_name:com.aliwo.algorithm.bubble
 *
 * @author:徐亚远 Date:2021/1/27 17:52
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/

public class BubbleTest {
    public static void main(String[] args) {
        Double [] arr = {4.0,5.3,1.4,6.5,33.2,2.2};
        Bubble.sort(arr);
        System.out.println("升序:"+ Arrays.toString(arr));
    }
}
