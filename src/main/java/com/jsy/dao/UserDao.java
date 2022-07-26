package com.jsy.dao;

import com.jsy.bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
@Repository()
public class UserDao{
    InputStream inputStream = UserDao.class.getClassLoader().getResourceAsStream("Config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

    public int addUser(User user){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInt userInt = sqlSession.getMapper(UserInt.class);
        int count = userInt.addUser(user);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public List<User> getAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInt userInt = sqlSession.getMapper(UserInt.class);
        List<User>userList = userInt.getAll();
        sqlSession.commit();
        sqlSession.close();
        return userList;
    }

    public User getUser(String name, String pwd){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInt userInt = sqlSession.getMapper(UserInt.class);
        User user = userInt.getUser(name,pwd);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    public int setUserPwd(String name, String pwd){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInt userInt = sqlSession.getMapper(UserInt.class);
        int count = userInt.setUserPwd(name,pwd);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        //User user1 = new User(0,"jsy123","1234567",1);
        //int count = userDao.addUser(user1, userDao.sqlSession);
        List<User> userList = userDao.getAll();
        System.out.println(userList);
    }

}

