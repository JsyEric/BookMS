package com.jsy.dao;

import com.jsy.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public interface UserInt {
    public int addUser(User user);
    public User getUser(@Param("name") String name, @Param("pwd") String pwd);
    public List<User> getAll();
    public int setUserPwd(@Param("name") String name, @Param("pwd") String pwd);

}
