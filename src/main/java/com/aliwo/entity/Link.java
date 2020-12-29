package com.aliwo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * package_name:com.aliwo.dao.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/19 13:04
 * 项目名:MyBlog
 * Description:TODO
 * Version: 1.0
 **/
@Setter
@Getter
public class Link implements Serializable {
    private static final long serialVersionUID = 395932087612876459L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 链接名称
     */
    private String linkName;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 排序序号 从小到大排序
     */
    private Integer orderNo;
    /**
     * 逻辑删除表示 0:不删除(默认), 1:删除
     * */
    private String dr;
}
