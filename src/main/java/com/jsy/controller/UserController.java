package com.jsy.controller;

import com.jsy.bean.User;
import com.jsy.biz.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {
    @Autowired
    UserBiz userBiz;
    @RequestMapping("/login.jsy")
    @ResponseBody
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession httpSession = req.getSession();
        String name = req.getParameter("name");
        String pwd = req.getParameter("pwd");
        String code = req.getParameter("valcode");
        String realCode = httpSession.getAttribute("code").toString();
        if(!code.equalsIgnoreCase(realCode))
            out.println("<script>alert('验证码错误');location.href='login.html';</script>");
        User user = userBiz.getUser(name,pwd);
        if(user == null){
            out.println("<script>alert('用户名或密码不存在');location.href='login.html';</script>");
        }else{
            httpSession.setAttribute("user",user);
            out.println("<script>alert('登陆成功');location.href='index.jsp';</script>");
        }
    }

    @RequestMapping("exit.jsy")
    public ModelAndView exit(HttpSession httpSession, ModelAndView modelAndView){
        httpSession.invalidate();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping("pwdModify.jsy")
    @ResponseBody
    public void exit(HttpServletRequest req, HttpSession httpSession, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        User curUser = (User) httpSession.getAttribute("user");
        String truePwd = curUser.getPwd();
        String pwd1 = req.getParameter("pwd");
        if(pwd1.equals(truePwd)){
            String newPwd1 = req.getParameter("newpwd");
            String newPwd2 = req.getParameter("newpwd2");
            if(newPwd1.equals(newPwd2)){
                userBiz.setUserPwd(curUser.getName(), newPwd1);
                httpSession.invalidate();
                out.println("<script>alert('密码修改成功');parent.window.location.href='login.html';</script>");
            }else{
                out.println("<script>alert('两次新密码不一致');</script>");
            }
        }else{
            out.println("<script>alert('原密码输入错误');</script>");
        }
    }
}
