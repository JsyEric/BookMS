package com.jsy.action;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.User;
import com.jsy.biz.UserBiz;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.let")
public class UserServlet extends HttpServlet {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
    UserBiz userBiz = app.getBean(UserBiz.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String method = req.getParameter("type");
        switch (method){
            case "login":
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
                break;
            case "exit":
                httpSession.invalidate();
                out.println("<script>parent.window.location.href='login.html';</script>");
                break;
            case "change":
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
}
