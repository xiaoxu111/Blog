package com.aliwo.algorithm.bubble;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * package_name:com.aliwo.algorithm.bubble
 *
 * @author:徐亚远 Date:2021/1/28 11:25
 * 项目名:Blog
 * Description: map的四种遍历方式
 * Version: 1.0
 **/

public class MapTest {

    /**
     * 初始化map键值对
     */
    public Map<String,Object> addMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("admin","123" );
        map.put("root","456" );
        map.put("xuyy19","xu1230" );
        map.put("22",11 );
        return map;
    }
    /**
     * map的第一种遍历方式  通过迭代器iterator遍历
     */
    @Test
    public void test01() {
        Map<String,Object> map = addMap();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key:"+key);
            System.out.println("value:"+value);
        }
    }

    /**
     *foreache循环遍历
     */
    @Test
    public void test02(){
        Map<String,Object> map = addMap();
        for (Map.Entry entry : map.entrySet()){
            System.out.println("key:"+entry.getKey()+"  " +"value:"+entry.getValue());
        }
    }

    /**
     * 在for循环中遍历key 或者value
     */
    @Test
    public void test03(){
        Map<String,Object> map = addMap();
        // for循环遍历Key
        for (Object key : map.keySet()){
            System.out.println("key:"+key);
        }
        // for循环遍历value
        for (Object value : map.values()){
            System.out.println("value:"+value);
        }
    }

    /**
     * 通过key得到value
     */
    @Test
    public void test04(){
        Map<String,Object> map = addMap();
        for (Object key : map.keySet()){
            Object value = map.get(key);
            System.out.println("value:"+value);
        }
    }
}
