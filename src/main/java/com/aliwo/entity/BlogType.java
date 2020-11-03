package com.aliwo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * package_name:com.aliwo.dao.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/19 13:01
 * 项目名:MyBlog
 * Description:TODO
 * Version: 1.0
 **/
@Setter
@Getter
public class BlogType implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;
    /**
     * 博客类型名称
     */
    private String typeName;
    /**
     * 数量，该类型下博客的数量
     */
    private Integer blogCount;
    /**
     * 排序  从小到大排序显示,序号
     */
    private Integer orderNo;
    /**
     * 逻辑删除表示 0:不删除(默认), 1:删除
     * */
    private String dr;
}
