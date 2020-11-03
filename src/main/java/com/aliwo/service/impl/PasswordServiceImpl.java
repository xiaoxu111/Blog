package com.aliwo.service.impl;

import com.aliwo.entity.PasswordVo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * package_name:com.aliwo.service.impl
 *
 * @author:徐亚远 Date:2020/4/24 16:34
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/
@Service
public class PasswordServiceImpl implements PasswordService
{
    //加密算法
    @Value("${algorithmName}")
    private String algorithmName;
    //散列次数
    @Value("${hashInterations}")
    private int hashInterations;
    @Override
    public String encryptPassword(Object passwordVo) throws IllegalArgumentException {
        PasswordVo vo = (PasswordVo) passwordVo;
        return new SimpleHash(algorithmName,vo.getPassword(),vo.getSalt(),hashInterations).toBase64();
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        return false;
    }
}
