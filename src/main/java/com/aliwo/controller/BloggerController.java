package com.aliwo.controller;

import com.aliwo.entity.Blogger;
import com.aliwo.service.BloggerService;
import com.aliwo.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 博主Controller层
 *
 * @author xuyy19
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @RequestMapping("/backend")
    public String backend() {
        return "forward:/backend/main.jsp";
    }

    /**
     * 用户登录
     *
     * @param blogger
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Blogger blogger, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), blogger.getPassword());
        try {
            subject.login(token); // 登录验证
            // 请求重定向
            return "redirect:/blogger/backend.do";
        } catch (UnknownAccountException e) {
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "用户名错误");
        } catch (IncorrectCredentialsException e) {
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "密码错误");
        } catch (AuthenticationException e) {
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "登陆失败");
        }
        return "login";
    }


    /**
     * 查找博主信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("blogger", bloggerService.find());
        mav.addObject("mainPage", "foreground/blogger/info.jsp");
        mav.addObject("pageTitle", "关于博主_IT小徐博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }
}
