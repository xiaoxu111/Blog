package com.aliwo.entity;

/**
 * package_name:com.aliwo.dao.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/19 12:47
 * 项目名:MyBlog
 * Description:TODO
 * Version: 1.0
 **/

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = -8211071483188348623L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 发布日期
     */
    private Date releaseDate;
    /**
     * 查看次数,点击数
     */
    private Integer clickHit;
    /**
     * 回复次数,评论数
     */
    private Integer replyHit;
    /**
     * 博客内容
     */
    private String content;
    /**
     * 博客内容 无网页标签 Lucene分词用,纯文本格式的内容
     */
    private String contentNoTag;
    /**
     * 博客类型,引入实体类
     */
    private BlogType blogType;
    /**
     * 博客数量 非博客实际属性，主要是 根据发布日期归档查询博客数量用
     */
    private Integer blogCount;
    /**
     * 发布日期字符串 年月月日,时分秒
     */
    private String releaseDateStr;
    /**
     * 关键字 空格隔开
     */
    private String keyWord;
    /**
     * 博客里存在的图片 主要用于列表展示显示缩略图
     */
    private List<String> imagesList = new LinkedList<String>();

    /**
     * 逻辑删除表示 0:不删除(默认), 1:删除
     */
    private String dr;


    /**
     * 每个博客对应的评论主表
     */
    private List<Comment> commentList;

}
