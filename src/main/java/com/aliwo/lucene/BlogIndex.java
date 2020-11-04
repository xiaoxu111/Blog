package com.aliwo.lucene;

import com.aliwo.entity.Blog;
import com.aliwo.util.DateUtil;
import com.aliwo.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客索引类
 *
 * @author Administrator
 */
public class BlogIndex {

    private Directory dir = null;

    /**
     * 获取IndexWriter实例
     *
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws Exception {
        // 中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        // 创建Directory目录对象，目录对象表示索引库的位置
        dir = FSDirectory.open(Paths.get("/data/lucene"));
        // 创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        // 建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter writer = new IndexWriter(dir, iwc);
        // 写入文档到索引库
        return writer;
    }

    /**
     * 添加博客索引
     *
     * @param blog
     */
    public void addIndex(Blog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new StringField("keyWord", blog.getKeyWord(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    /**
     * 更新博客索引
     *
     * @param blog
     * @throws Exception
     */
    public void updateIndex(Blog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new StringField("keyWord", blog.getKeyWord(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    /**
     * 删除指定博客的索引
     *
     * @param blogId
     * @throws Exception
     */
    public void deleteIndex(String blogId) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", blogId));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

    /**
     * 查询博客信息
     *
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<Blog> searchBlog(String q) throws Exception {
        dir = FSDirectory.open(Paths.get("/data/lucene"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);
        QueryParser parser4 = new QueryParser("releaseDate", analyzer);
        Query query4 = parser4.parse(q);
        QueryParser parser5 = new QueryParser("summary", analyzer);
        Query query5 = parser5.parse(q);
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query4, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query5, BooleanClause.Occur.SHOULD);
        // 最多返回100条数据
        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        // 高亮
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<Blog> blogList = new LinkedList<Blog>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(doc.get(("id"))));
            blog.setReleaseDateStr(doc.get(("releaseDate")));

            String title = doc.get("title");

            String content = StringEscapeUtils.escapeHtml(doc.get("content"));

            String releaseDate = StringEscapeUtils.escapeHtml(doc.get("releaseDate"));

            String summary = doc.get("summary");
            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (StringUtil.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (StringUtil.isEmpty(hContent)) {
                    if (content.length() <= 200) {
                        blog.setContent(content);
                    } else {
                        blog.setContent(content.substring(0, 200));
                    }
                } else {
                    blog.setContent(hContent);
                }
            }
            if (releaseDate != null) {
                TokenStream tokenStream = analyzer.tokenStream("releaseDate", new StringReader(releaseDate));
                String hReleaseDate = highlighter.getBestFragment(tokenStream, releaseDate);
                if (StringUtil.isEmpty(hReleaseDate)) {
                    if (releaseDate.length() <= 200) {
                        blog.setReleaseDateStr(releaseDate);
                    } else {
                        blog.setReleaseDateStr(releaseDate.substring(0, 200));
                    }
                } else {
                    blog.setReleaseDateStr(hReleaseDate);
                }
            }
            if (summary != null) {
                TokenStream tokenStream = analyzer.tokenStream("summary", new StringReader(summary));
                String hSummary = highlighter.getBestFragment(tokenStream, summary);
                if (StringUtil.isEmpty(hSummary)) {
                    if (summary.length() <= 200) {
                        blog.setSummary(summary);
                    } else {
                        blog.setSummary(summary.substring(0, 200));
                    }
                } else {
                    blog.setSummary(hSummary);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }
}
