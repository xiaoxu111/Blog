package com.aliwo.entity;

import lombok.Getter;
import lombok.Setter;

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
public class Blogger{
    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 个人简介
     */
    private String proFile;
    /**
     * 博主头像
     */
    private String imageName;
    /**
     * 逻辑删除表示 0:不删除(默认), 1:删除
     * */
    private String dr;
}
