package com.aliwo.controller.admin;

import com.aliwo.entity.Blog;
import com.aliwo.entity.BlogType;
import com.aliwo.entity.Blogger;
import com.aliwo.entity.Link;
import com.aliwo.service.BlogService;
import com.aliwo.service.BlogTypeService;
import com.aliwo.service.BloggerService;
import com.aliwo.service.LinkService;
import com.aliwo.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理员系统Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/system")
@ResponseBody
public class SystemAdminController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private LinkService linkService;

    /**
     * 刷新系统缓存
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletResponse response, HttpServletRequest request) throws Exception {
        ServletContext application = RequestContextUtils.findWebApplicationContext(request).getServletContext();
        /** 查询博主信息*/
        Blogger blogger = bloggerService.find();
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);

        /**查询博客类别以及博客的数量 */
        List<BlogType> blogTypeCountList = blogTypeService.countList();
        application.setAttribute("blogTypeCountList", blogTypeCountList);


        /**  根据日期分组查询博客*/
        List<Blog> blogCountList = blogService.countList();
        application.setAttribute("blogCountList", blogCountList);
        //根据博客点击次数可视化分析数据
        List<Blog> hotBlogList = blogService.getHotBlog();
        application.setAttribute("hotBlogList", hotBlogList);

        /**获取所有友情链接 */
        List<Link> linkList = linkService.list(null);
        application.setAttribute("linkList", linkList);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
