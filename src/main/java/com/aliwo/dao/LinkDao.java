package com.aliwo.dao;

import com.aliwo.entity.Link;

import java.util.List;
import java.util.Map;
/**
 * package_name:com.aliwo.dao
 *
 * @author:徐亚远 Date:2020/4/30 15:41
 * 项目名:Blog
 * 博主Dao接口
 * Description:友情链接Dao接口
 * Version: 1.0
 **/
public interface LinkDao {

	/**
	 * 添加友情链接
	 * @param link
	 * @return
	 */
	int add(Link link);
	
	/**
	 * 修改友情链接
	 * @param link
	 * @return
	 */
	int update(Link link);
	
	/**
	 * 查找友情链接信息
	 * @param map
	 * @return
	 */
	List<Link> list(Map<String, Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	Long getTotal(Map<String, Object> map);
	
	/**
	 * 删除友情链接
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);
}
