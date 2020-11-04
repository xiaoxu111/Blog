package com.aliwo.controller;

import com.aliwo.entity.Blog;
import com.aliwo.lucene.BlogIndex;
import com.aliwo.service.BlogService;
import com.aliwo.service.CommentService;
import com.aliwo.util.DateUtil;
import com.aliwo.util.StringUtil;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.*;

/**
 * 博客Controller层
 *
 * @author xuyy19
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * 博客索引
     */
    private BlogIndex blogIndex = new BlogIndex();
    // lucene 索引库存储位置
    private Directory dir = null;
    // 初始化加载索引库标志
    private static Boolean FLAG = false;

    /**
     * 请求博客详细信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        // 根据主键查询博客信息
        Blog blog = blogService.findById(id);
        // 处理关键字
        String keyWords = blog.getKeyWord();
        if (StringUtil.isNotEmpty(keyWords)) {
            String[] arr = keyWords.split(" ");
            // 重新 new ArrayList<>() 数组转换为list
            mav.addObject("keyWords", StringUtil.filterWhite(new ArrayList<String>(Arrays.asList(arr))));
        } else {
            mav.addObject("keyWords", null);
        }
        mav.addObject("blog", blog);
        /** 博客点击次数加1*/
        blog.setClickHit(blog.getClickHit() + 1);
        blogService.update(blog);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("blogId", blog.getId());
        /** 查询审核通过的评论*/
        map.put("state", 1);
        mav.addObject("commentList", commentService.list(map));
        // 上一篇下一篇
        mav.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id),
                request.getServletContext().getContextPath()));
        mav.addObject("mainPage", "foreground/blog/view.jsp");
        mav.addObject("pageTitle", blog.getTitle() + "_IT小徐博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 创建索引库,容器启动时就创建索引库
     *
     * @throws IOException
     */
    public Boolean createIndex(Boolean flag) throws IOException {
        if (FLAG == true) {
            return true;
        }
        // 采集数据
        List<Blog> blogList = blogService.queryListBlog();
        // 文档集合
        List<Document> documentList = new ArrayList<>();
        for (Blog blog : blogList) {
            // 创建文档对象
            Document document = new Document();
            // 创建域对象,并且放入文档对象中
            document.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
            document.add(new TextField("title", blog.getTitle(), Field.Store.YES));
            document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store
                    .YES));
            document.add(new TextField("content", blog.getContent(), Field.Store.YES));
            document.add(new TextField("summary", blog.getSummary(), Field.Store.YES));
            // 文档对象放入文档集合中
            documentList.add(document);
        }
        // 中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        // 创建Directory目录对象，目录对象表示索引库的位置
        dir = FSDirectory.open(Paths.get("/data/lucene"));
        // 创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        // 建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter writer = new IndexWriter(dir, iwc);
        // 写入文档到索引库
        for (Document doc : documentList) {
            writer.addDocument(doc);
        }
        writer.close();
        return FLAG = true;
    }

    /**
     * 容器停止的时候删除索引文件lucene
     */
    public void contextDestroyed() {
        // TODO Auto-generated method stub
        delFolder("/data/lucene");
    }

    /***
     * 删除指定文件夹下所有文件,当容器停止的时候
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /***
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据关键字查询相关博客信息
     *
     * @param q
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q, @RequestParam(value = "page",
            required = false) String page, HttpServletRequest request) throws Exception {
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "foreground/blog/result.jsp");
        // 创建索引库,只在第一次搜索的时候创建索引库
        this.createIndex(true);
        List<Blog> blogList = blogIndex.searchBlog(q.trim());
        Integer toIndex = blogList.size() >= Integer.parseInt(page) * 10 ? Integer.parseInt(page) * 10 : blogList
                .size();
        mav.addObject("blogList", blogList.subList((Integer.parseInt(page) - 1) * 10, toIndex));

        mav.addObject("pageCode", this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, 10, request
                .getServletContext().getContextPath()));
        mav.addObject("q", q);
        mav.addObject("resultTotal", blogList.size());
        mav.addObject("pageTitle", "搜索关键字'" + q + "'结果页面_IT小徐博客系统");
        mav.setViewName("mainTemp");
        //this.contextDestroyed();
        return mav;
    }

    /**
     * 获取下一篇博客和下一篇博客代码
     *
     * @param lastBlog
     * @param nextBlog
     * @return
     */
    private String genUpAndDownPageCode(Blog lastBlog, Blog nextBlog, String projectContext) {
        StringBuffer pageCode = new StringBuffer();
        if (lastBlog == null || lastBlog.getId() == null) {
            pageCode.append("<p>上一篇：没有了</p>");
        } else {
            pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + ".html'>" +
                    lastBlog.getTitle() + "</a></p>");
        }
        if (nextBlog == null || nextBlog.getId() == null) {
            pageCode.append("<p>下一篇：没有了</p>");
        } else {
            pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + ".html'>" +
                    nextBlog.getTitle() + "</a></p>");
        }
        return pageCode.toString();
    }

    /**
     * 获取上一页，下一页代码 查询博客用到
     *
     * @param page           当前页
     * @param totalNum       总记录数
     * @param q              查询关键字
     * @param pageSize       每页大小
     * @param projectContext
     * @return
     */
    private String genUpAndDownPageCode(Integer page, Integer totalNum, String q, Integer pageSize, String
            projectContext) {
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0) {
            return "";
        } else {
            pageCode.append("<nav>");
            pageCode.append("<ul class='pager' >");
            if (page > 1) {
                pageCode.append
                        ("<li><a href='" + projectContext + "/blog/q.html?page=" + (page - 1) + "&q=" + q + "'>上一页</a" +
                                "></li>");
            } else {
                pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
            }
            if (page < totalPage) {
                pageCode.append
                        ("<li><a href='" + projectContext + "/blog/q.html?page=" + (page + 1) + "&q=" + q + "'>下一页</a" +
                                "></li>");
            } else {
                pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
            }
            pageCode.append("</ul>");
            pageCode.append("</nav>");
        }
        return pageCode.toString();
    }

    /**
     * @return 根据博客浏览量可视化分析数据
     */
    @RequestMapping(value = "/hotBlogList", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> getHotBlog() {
        List<Blog> hotBlogList = blogService.getHotBlog();
        return hotBlogList;
    }

    /**
     * @return 根据博客每月发布数量可视化分析数据
     */
    @RequestMapping(value = "/blogYearAndMonth", method = RequestMethod.POST)
    @ResponseBody
    public List<Blog> getHotBlogByYearAndMonth() {
        List<Blog> blogYearList = blogService.countList();
        return blogYearList;
    }

    /**
     * @return
     * 博客访问人数
     */
    //@RequestMapping(value = "/getTotalAccessor",method = RequestMethod.POST)
    //@ResponseBody
    //public BigInteger getTotalAccessor() {
    //    BigInteger bigInteger = blogService.getTotalAccessor();
    //    return bigInteger;
    //}

    /**
     * @return
     * 博客总点击次数
     */
    //@RequestMapping(value = "/getTotalHit",method = RequestMethod.POST)
    //@ResponseBody
    //public BigInteger getTotalHit() {
    //    BigInteger bigInteger = blogService.getTotalHit();
    //    return bigInteger;
    //}


}
