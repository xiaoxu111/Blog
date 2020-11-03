package com.aliwo.dao;

import com.aliwo.entity.BlogType;

import java.util.List;
import java.util.Map;
/**
 * package_name:com.aliwo.dao
 *
 * @author:徐亚远 Date:2020/4/30 15:41
 * 项目名:Blog
 * 博客类型Dao接口
 * Description:TODO
 * Version: 1.0
 **/
public interface BlogTypeDao {

	/**
	 * 查询所有博客类型 以及对应的博客数量,无参数查询
	 * @return
	 */
	List<BlogType> countList();
	
	/**
	 * 通过id查询博客类型
	 * @param id
	 * @return
	 */
	BlogType findById(Integer id);
	
	/**
	 * 分页查询博客类别信息,不固定参数
	 * @param map
	 * @return
	 */
	List<BlogType> list(Map<String, Object> map);
	
	/**
	 * 获取总记录数,不固定参数
	 * @param map
	 * @return
	 */
	Long getTotal(Map<String, Object> map);
	
	/**
	 * 添加博客类别信息
	 * @param blogType
	 * @return
	 */
	Integer add(BlogType blogType);
	
	/**
	 * 修改博客类别信息
	 * @param blogType
	 * @return
	 */
	Integer update(BlogType blogType);
	
	/**
	 * 删除博客类别信息
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);
}
