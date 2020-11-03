package com.aliwo.dao;

/**
 * package_name:com.aliwo.dao
 *
 * @author:徐亚远 Date:2020/4/30 15:41
 * 项目名:Blog
 * Description:TODO
 * Version: 1.0
 **/
import com.aliwo.entity.Blog;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 博客Dao接口
 * @author Administrator
 *
 */
public interface BlogDao {

    /**
     * 根据日期月份分组查询,无参数查询博客列表,首页的时候使用
     * @return
     */
    List<Blog> countList();

    /**
     * 分页查询博客,带参数查询博客列表
     * @param map
     * @return
     */
    List<Blog> list(Map<String, Object> map);

    /**
     * 获取总记录数,查询博客数量
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 通过Id查找实体,查询博客信息
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
     * 按博客点击次数(热度)可视化分析数据
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
}

