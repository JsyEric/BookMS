package com.jsy.dao;

import com.jsy.bean.Type;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
@Repository
public class TypeDao {
    InputStream inputStream = UserDao.class.getClassLoader().getResourceAsStream("Config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

    public int add(String name, long parentId) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TypeInt typeInt = sqlSession.getMapper(TypeInt.class);
        int count = typeInt.add(name,parentId);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public List<Type> getAll() throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TypeInt typeInt = sqlSession.getMapper(TypeInt.class);
        List<Type> typeList = typeInt.getAll();
        sqlSession.commit();
        sqlSession.close();
        return typeList;

    }

    public Type getById(long typeId) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TypeInt typeInt = sqlSession.getMapper(TypeInt.class);
        Type type = typeInt.getById(typeId);
        sqlSession.commit();
        sqlSession.close();
        return type;
    }

    public int modify(long id, String name, long parentId) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TypeInt typeInt = sqlSession.getMapper(TypeInt.class);
        int count = typeInt.modify(id,name,parentId);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public int remove(long id) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TypeInt typeInt = sqlSession.getMapper(TypeInt.class);
        int count = typeInt.remove(id);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public static void main(String[] args) throws SQLException {
        TypeDao typeDao = new TypeDao();
        System.out.println(typeDao.getAll());
    }
}
