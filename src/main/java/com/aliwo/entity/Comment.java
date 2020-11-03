package com.aliwo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * package_name:com.aliwo.dao.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/19 13:03
 * 项目名:MyBlog
 * Description:TODO
 * Version: 1.0
 **/
@Setter
@Getter
public class Comment {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户IP
     */
    private String userIp;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 被评论的博客
     */
    private Blog blog;
    /**
     * 评论日期
     */
    private Date commentDate;
    /**
     * 审核状态  0 待审核 1 审核通过 2 审核未通过
     */
    private Integer state;
    /**
     * 逻辑删除表示 0:不删除(默认), 1:删除
     * */
    private String dr;
}
