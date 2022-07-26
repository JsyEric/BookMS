package com.jsy.action;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.Type;
import com.jsy.biz.TypeBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/type.let")
public class TypeServlet extends HttpServlet {


    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
    TypeBiz typeBiz = app.getBean(TypeBiz.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    /**
     * /type.let?type = add,modifypre,modify,remove&id,
     * /type_list.jsp
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext application = req.getServletContext();
        String type = req.getParameter("type");
        switch (type){
            case "add":
                String typeName = req.getParameter("typeName");
                long parentId = Long.parseLong(req.getParameter("parentType"));
                int count = typeBiz.add(typeName,parentId);
                if(count>0){
                    List<Type> typeList = typeBiz.getAll();
                    application.setAttribute("typeList", typeList);
                    out.println("<script>alert('修改类型成功');location.href='type_list.jsp';</script>");
                }else{
                    out.println("<script>alert('修改类型失败');location.href='type_add.jsp';</script>");
                }
                break;
            case "modifypre":
                long id1 = Long.parseLong(req.getParameter("id"));
                Type type1 = typeBiz.getById(id1);
                req.setAttribute("type",type1);
                req.getRequestDispatcher("type_modify.jsp").forward(req,resp);
                break;
            case "modify":
                long id2 = Long.parseLong(req.getParameter("typeId"));
                String name = req.getParameter("typeName");
                long parentId1 = Long.parseLong(req.getParameter("parentType"));
                int count2 = typeBiz.modify(id2,name,parentId1);

                if(count2>0){
                    List<Type> typeList = typeBiz.getAll();
                    application.setAttribute("typeList", typeList);
                    out.println("<script>alert('修改类型成功');location.href='type_list.jsp';</script>");
                }else{
                    out.println("<script>alert('修改类型失败');location.href='type_list.jsp';</script>");
                }



                break;
            case "remove":
                long id = Long.parseLong(req.getParameter("id"));
                int count1 = 0;
                try {
                    count1 = typeBiz.remove(id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(count1>0){
                    List<Type> typeList = typeBiz.getAll();
                    application.setAttribute("typeList", typeList);
                    out.println("<script>alert('删除类型成功');location.href='type_list.jsp';</script>");
                }else{
                    out.println("<script>alert('删除类型失败');location.href='type_list.jsp';</script>");
                }
                break;
        }
    }

}
