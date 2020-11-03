package com.aliwo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * package_name:com.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/24 16:39
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/
@Setter
@Getter
public class PasswordVo implements Serializable {
    /**
     * 加密算法
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
}
