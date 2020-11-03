package com.aliwo.controller.admin;

import com.aliwo.entity.Comment;
import com.aliwo.entity.PageBean;
import com.aliwo.service.CommentService;
import com.aliwo.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员评论Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/comment")
@ResponseBody
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    /**
     * 分页查询评论信息
     *
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows",
            required = false) String rows, @RequestParam(value = "state", required = false) String state,
                       HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        //加载翻页,状态参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        /**评论状态 */
        map.put("state", state);
        //查询评论列表
        List<Comment> commentList = commentService.list(map);
        //查询评论总数
        Long total = commentService.getTotal(map);

        JSONObject result = new JSONObject();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 删除评论信息
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 评论审核
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/review")
    public String review(@RequestParam(value = "ids") String ids, @RequestParam(value = "state") Integer state,
                         HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            Comment comment = new Comment();
            comment.setState(state);
            comment.setId(Integer.parseInt(idsStr[i]));
            commentService.update(comment);
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
