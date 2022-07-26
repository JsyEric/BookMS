package com.jsy.biz;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.User;
import com.jsy.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
@Service
public class UserBiz {
    @Autowired
    UserDao userDao;
    public User getUser(String name, String pwd){
        User user = null;
        user = userDao.getUser(name,pwd);

        return user;
    }

    public void setUserPwd(String name, String pwd){
            userDao.setUserPwd(name,pwd);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        UserBiz userBiz = app.getBean(UserBiz.class);
        System.out.println(userBiz.getUser("super","123"));
    }
}
