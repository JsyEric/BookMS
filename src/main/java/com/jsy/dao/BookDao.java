package com.jsy.dao;

import com.jsy.bean.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
@Repository
public class BookDao {
    InputStream inputStream = UserDao.class.getClassLoader().getResourceAsStream("Config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    public List<Book> getByTypeId(long typeId) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        List<Book> bookList = bookInt.getByTypeId(typeId);
        sqlSession.commit();
        sqlSession.close();
        return bookList;
    }

    public int add(long typeId, String name, float price, String desc, String pic, String publish, String author,long stock,String address) throws SQLException{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        int count = bookInt.add(typeId,name,price,desc,pic,publish,author,stock,address);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public int modify(long id, long typeId, String name, float price, String desc, String pic, String publish, String author,long stock,String address) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        int count = bookInt.modify(id,typeId,name,price,desc,pic,publish,author,stock,address);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public int remove(long id) throws SQLException{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        int count = bookInt.remove(id);
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public List<Book> getByPage(int pageIndex, int pageSize) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        List<Book> bookList = bookInt.getByPage((pageIndex-1)*pageSize,pageSize);
        sqlSession.commit();
        sqlSession.close();
        return bookList;
    }

    public Book getById(long id) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        Book book = bookInt.getById(id);
        sqlSession.commit();
        sqlSession.close();
        return book;
    }

    public int getCount() throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookInt bookInt = sqlSession.getMapper(BookInt.class);
        int count = bookInt.getCount();
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public static void main(String[] args) throws SQLException {
        BookDao bookDao = new BookDao();
        System.out.println(bookDao.getCount());
    }
}
