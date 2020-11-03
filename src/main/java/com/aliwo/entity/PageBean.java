package com.aliwo.entity;


/**
 * package_name:com.aliwo.dao.aliwo.entity
 *
 * @author:徐亚远 Date:2020/4/19 13:05
 * 项目名:MyBlog
 * Description:TODO
 * Version: 1.0
 **/
public class PageBean {
    /**
     * 当前第几页,从1开始
     */
    private int page;
    /**
     * 每页记录数,页面大小
     */
    private int pageSize;
    /**
     * 起始页,从第几条数据开始查询
     */
    private int start;

    public void setStart(int start) {
        this.start = start;
    }

    public PageBean(int page, int pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page - 1) * this.pageSize;
    }

}
