package com.aliwo.dao;

import com.aliwo.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * package_name:com.aliwo.dao
 *
 * @author:徐亚远 Date:2020/4/30 15:41
 * 项目名:Blog
 * 博主Dao接口
 * Description:评论Dao接口
 * Version: 1.0
 **/
public interface CommentDao {

	/**
	 * 添加评论信息
	 * @param comment
	 * @return
	 */
	int add(Comment comment);
	
	/**
	 * 修改评论信息
	 * @param comment
	 * @return
	 */
	int update(Comment comment);
	
	/**
	 * 查找用户评论信息
	 * @param map
	 * @return
	 */
	List<Comment> list(Map<String, Object> map);
	
	/**
	 * 获取总记录数,评论数量
	 * @param map
	 * @return
	 */
	Long getTotal(Map<String, Object> map);
	
	/**
	 * 删除评论信息
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);
	
	
}
