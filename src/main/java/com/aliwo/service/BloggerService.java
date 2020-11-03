package com.aliwo.service;

import com.aliwo.entity.Blogger;

/**
 * 博主Service接口
 * @author java1234_小锋
 *
 */
public interface BloggerService {

	/**
	 * 查询博主信息
	 * @return
	 */
	Blogger find();
	
	/**
	 * 通过用户名查询用户
	 * @param userName
	 * @return
	 */
	Blogger getByUserName(String userName);
	
	/**
	 * 更新博主信息
	 * @param blogger
	 * @return
	 */
	 Integer update(Blogger blogger);
}
