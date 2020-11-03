package com.aliwo.dao;

import com.aliwo.entity.Blogger;
/**
 * package_name:com.aliwo.dao
 *
 * @author:徐亚远 Date:2020/4/30 15:41
 * 项目名:Blog
 * 博主Dao接口
 * Description:TODO
 * Version: 1.0
 **/

public interface BloggerDao {

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
