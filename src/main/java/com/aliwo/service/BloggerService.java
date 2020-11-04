package com.aliwo.service;

import com.aliwo.entity.Blogger;

/**
 * 博主Service接口
 * @author xuyy19
 *
 */
public interface BloggerService {

	/**
	 * 查询博主信息
	 *
	 */
	Blogger find();
	
	/**
	 * 通过用户名查询用户
	 * @param userName
	 *
	 */
	Blogger getByUserName(String userName);
	
	/**
	 * 更新博主信息
	 * @param blogger
	 *
	 */
	 Integer update(Blogger blogger);
}
