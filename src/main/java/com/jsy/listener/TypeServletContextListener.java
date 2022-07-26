package com.jsy.listener;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.Type;
import com.jsy.biz.TypeBiz;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class TypeServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        TypeBiz typeBiz = app.getBean(TypeBiz.class);
        List<Type> typeList = typeBiz.getAll();

        ServletContext application = servletContextEvent.getServletContext();
        application.setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
