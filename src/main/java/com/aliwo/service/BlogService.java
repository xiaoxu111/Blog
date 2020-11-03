package com.aliwo.service;

import com.aliwo.entity.Blog;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 博客Service接口
 * @author Administrator
 *
 */
public interface BlogService {

	/**
	 * 根据日期月份分组查询
	 * @return
	 */
	 List<Blog> countList();
	
	/**
	 * 分页查询博客
	 * @param map
	 * @return
	 */
	List<Blog> list(Map<String, Object> map);

	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	 Long getTotal(Map<String, Object> map);
	
	/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
  Blog findById(Integer id);
	
	/**
	 * 更新博客信息
	 * @param blog
	 * @return
	 */
	Integer update(Blog blog);
	
	/**
	 * 获取上一个博客
	 * @param id
	 * @return
	 */
	Blog getLastBlog(Integer id);
	
	/**
	 * 获取下一个博客
	 * @param id
	 * @return
	 */
	Blog getNextBlog(Integer id);
	
	/**
	 * 添加博客信息
	 * @param blog
	 * @return
	 */
	Integer add(Blog blog);
	
	/**
	 * 删除博客信息
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);
	
	/**
	 * 查询指定的博客类别下的博客数量
	 * @param typeId
	 * @return
	 */
	Integer getBlogByTypeId(Integer typeId);
    /**
	 * 可视化分析根据博客点击次数(热度最高)
	 * */
	List<Blog> getHotBlog();


	/**
	 * 查询所有的博客内容,加载进lucene文档
	 * @return Blog
	 */
	List<Blog> queryListBlog();

    /**
     * @return
     * 博客访问人数
     */
	//BigInteger getTotalAccessor();

    /**
     * @return
     * 博客总点击次数
     */
	//BigInteger getTotalHit();

	/**
	 * 创建索引库,容器启动时就创建索引库
	 * @throws IOException
	 */
	Boolean createIndex() throws IOException;
}
