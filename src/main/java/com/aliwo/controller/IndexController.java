package com.aliwo.controller;

import com.aliwo.entity.Blog;
import com.aliwo.entity.PageBean;
import com.aliwo.service.BlogService;
import com.aliwo.util.PageUtil;
import com.aliwo.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页Controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private BlogService blogService;


    /**
     * 请求主页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page", required = false) String page, @RequestParam(value =
            "typeId", required = false) String typeId, @RequestParam(value = "releaseDateStr", required = false)
                                      String releaseDateStr, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        //一页显示十条数据
        PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<Blog> blogList = blogService.list(map);
        for (Blog blog : blogList) {
            List<String> imagesList = blog.getImagesList();
            String blogInfo = blog.getContent();
            //添加lucene
            Document doc = Jsoup.parse(blogInfo);
            /**查找扩展名是jpg的图片 */
            Elements jpgs = doc.select("img[src$=.jpg]");
            for (int i = 0; i < jpgs.size(); i++) {
                Element jpg = jpgs.get(i);
                imagesList.add(jpg.toString());
                if (i == 2) {
                    break;
                }
            }
        }
        mav.addObject("blogList", blogList);
        /**查询参数 */
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(typeId)) {
            param.append("typeId=" + typeId + "&");
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("releaseDateStr=" + releaseDateStr + "&");
        }
        //request.getContextPath() 获取项目路径
       String pageCode = PageUtil.genPagination(request.getContextPath() + "/index.html", blogService
                .getTotal(map), Integer.parseInt(page), 10, param.toString());
        //添加分页功能
        mav.addObject("pageCode",pageCode);
        mav.addObject("mainPage", "foreground/blog/list.jsp");
        mav.addObject("pageTitle", "IT小徐博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 源码下载
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ModelAndView download() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "foreground/system/download.jsp");
        mav.addObject("pageTitle", "本站源码下载页面_Java开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }
}
