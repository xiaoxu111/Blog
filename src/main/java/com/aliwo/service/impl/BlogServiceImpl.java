package com.aliwo.service.impl;

import com.aliwo.dao.BlogDao;
import com.aliwo.entity.Blog;
import com.aliwo.service.BlogService;
import com.aliwo.util.DateUtil;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 博客Service实现类
 * @author xuyy19
 *
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao blogDao;
    private Directory dir = null;
	
	@Override
    public List<Blog> countList() {
		return blogDao.countList();
	}

	@Override
	public List<Blog> list(Map<String, Object> map) {
		return blogDao.list(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return blogDao.getTotal(map);
	}

	@Override
	public Blog findById(Integer id) {
		return blogDao.findById(id);
	}

	@Override
	public Integer update(Blog blog) {
		return blogDao.update(blog);
	}

	@Override
	public Blog getLastBlog(Integer id) {
		return blogDao.getLastBlog(id);
	}

	@Override
	public Blog getNextBlog(Integer id) {
		return blogDao.getNextBlog(id);
	}

	@Override
	public Integer add(Blog blog) {
		return blogDao.add(blog);
	}

	@Override
	public Integer delete(Integer id) {
		return blogDao.delete(id);
	}

	@Override
	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

	@Override
	public List<Blog> getHotBlog() {
		return blogDao.getHotBlog();
	}

	@Override
	public List<Blog> queryListBlog()  {
		return blogDao.queryListBlog();
	}

    /**
     * @return 博客访问人数
     */
    //@Override
    //public BigInteger getTotalAccessor() {
    //    return blogDao.getTotalAccessor();
    //}

    /**
     * @return 博客总点击次数
     */
   /* @Override
    public BigInteger getTotalHit() {
        return blogDao.getTotalHit();
    }*/

    /**
     * 创建索引库,容器启动时就创建索引库
     * @throws IOException
     */
    @Override
    public Boolean createIndex() throws IOException {
        // 采集数据
        List<Blog> blogList = blogDao.queryListBlog();
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
        dir = FSDirectory.open(Paths.get("/lucene"));
        // 创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        // 建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter writer = new IndexWriter(dir, iwc);
        // 写入文档到索引库
        for (Document doc : documentList) {
            writer.addDocument(doc);
        }
        writer.close();
        return true;
    }


}
