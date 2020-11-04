package com.aliwo.controller;

import com.aliwo.entity.Blog;
import com.aliwo.entity.Comment;
import com.aliwo.service.BlogService;
import com.aliwo.service.CommentService;
import com.aliwo.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 评论Controller层
 *
 * @author xuyy19
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    /**
     * 添加或者修改评论
     *
     * @param comment
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
                       HttpServletResponse response, HttpSession session) throws Exception {
        // 获取系统生成的验证码
        String sRand = (String) session.getAttribute("sRand");
        JSONObject result = new JSONObject();
        // 操作的记录条数
        int resultTotal = 0;
        if (!imageCode.equals(sRand)) {
            result.put("success", false);
            result.put("errorInfo", "验证码填写错误！");
        } else {
            // 获取用户IP
            String userIp = request.getRemoteAddr();
            comment.setUserIp(userIp);
            if (comment.getId() == null) {
                resultTotal = commentService.add(comment);
                // 该博客的回复次数加1
                Blog blog = blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit() + 1);
                blogService.update(blog);
            } else {

            }
            if (resultTotal > 0) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        }
        ResponseUtil.write(response, result);
        return null;
    }

}
