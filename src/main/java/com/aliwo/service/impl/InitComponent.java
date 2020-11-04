package com.aliwo.service.impl;

import com.aliwo.controller.BlogController;
import com.aliwo.entity.Blog;
import com.aliwo.entity.BlogType;
import com.aliwo.entity.Blogger;
import com.aliwo.entity.Link;
import com.aliwo.service.BlogService;
import com.aliwo.service.BlogTypeService;
import com.aliwo.service.BloggerService;
import com.aliwo.service.LinkService;
import com.aliwo.util.DateUtil;
import lombok.SneakyThrows;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 初始化组件 把博主信息 根据博客类别分类信息 根据日期归档分类信息 存放到application中，用以提供页面请求性能
 *
 * @author xuyy19
 */
@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;
    //private Directory dir = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        InitComponent.applicationContext = applicationContext;
    }

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();

        BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
        /** 查询博主信息*/
        Blogger blogger = bloggerService.find();
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);

        BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
        /** 查询博客类别以及博客的数量*/
        List<BlogType> blogTypeCountList = blogTypeService.countList();
        application.setAttribute("blogTypeCountList", blogTypeCountList);

        BlogService blogService = (BlogService) applicationContext.getBean("blogService");
        /**根据日期分组查询博客 ,按年月分类的博客数量*/
        List<Blog> blogCountList = blogService.countList();
        application.setAttribute("blogCountList", blogCountList);
        /// 创建索引库,容器启动时就创建索引库
        /// blogService.createIndex();
        LinkService linkService = (LinkService) applicationContext.getBean("linkService");
        /** 查询所有的友情链接信息*/
        List<Link> linkList = linkService.list(null);
        application.setAttribute("linkList", linkList);
    }

   /* *//**
     * 创建索引库,容器启动时就创建索引库
     * @throws IOException
     *//*
    private void createIndex() throws IOException {
        BlogService blogService = (BlogService) applicationContext.getBean("blogService");
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
    }*/

    /**
     * @param sce
     * 容器停止的时候删除索引文件lucene
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        delFolder("/data/lucene");
    }

    /***
     * 删除指定文件夹下所有文件,当容器停止的时候
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static  boolean delAllFile(String path) {
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
    public  static void delFolder(String folderPath) {
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
}
